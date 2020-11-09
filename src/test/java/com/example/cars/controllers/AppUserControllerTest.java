package com.example.cars.controllers;

import com.example.cars.entities.AppUser;
import com.example.cars.repositories.AppUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import org.springframework.security.test.context.support.WithMockUser;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@AutoConfigureRestDocs(uriPort = 8080)
public class AppUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AppUserRepository appUserRepository;

    @Test
    @WithMockUser(value = "admin", roles = "ADMIN")
    void findAllUsers() throws Exception{
        given(appUserRepository.findAll()).willReturn(List.of(AppUser.builder().build()));

        mockMvc.perform(get("/api/v1/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/users-get-all"));
    }

    @Test
    @WithMockUser(value = "admin", roles = "ADMIN")
    void findUserById() throws Exception{
        given(appUserRepository.findById(any())).willReturn(Optional.of(AppUser.builder().build()));

        mockMvc.perform(get("/api/v1/users/{id}", UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/users-get-one",
                        pathParameters(
                                parameterWithName("id").description("UUID string of desired user to get.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("User ID"),
                                fieldWithPath("firstname").description("First Name"),
                                fieldWithPath("lastname").description("Last Name"),
                                fieldWithPath("birthday").description("Birthday").type(LocalDate.class),
                                fieldWithPath("email").description("Email"),
                                fieldWithPath("phone").description("Phone"),
                                fieldWithPath("username").description("User Name"),
                                fieldWithPath("password").description("Password"),
                                fieldWithPath("acl").description("ACL")
                        )));
    }

    @Test
    @WithMockUser(value = "admin", roles = "ADMIN")
    void saveUser() throws Exception{
        AppUser user = getValidUser();
        String userJson = objectMapper.writeValueAsString(user);

        given(appUserRepository.save(any())).willReturn(AppUser.builder().build());

        ConstrainedFields field = new ConstrainedFields(AppUser.class);

        mockMvc.perform(put("/api/v1/users/{id}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isNoContent())
                .andDo(document("/v1/users-new",
                        requestFields(
                                fieldWithPath("id").description("User ID"),
                                fieldWithPath("firstname").description("First Name"),
                                fieldWithPath("lastname").description("Last Name"),
                                fieldWithPath("birthday").description("Birthday"),
                                fieldWithPath("email").description("Email"),
                                fieldWithPath("phone").description("Phone"),
                                fieldWithPath("username").description("User Name"),
                                fieldWithPath("password").description("Password"),
                                fieldWithPath("acl").description("ACL")
                        ),
                        responseFields(
                                fieldWithPath("id").description("User ID"),
                                fieldWithPath("firstname").description("First Name"),
                                fieldWithPath("lastname").description("Last Name"),
                                fieldWithPath("birthday").description("Birthday").type(LocalDate.class),
                                fieldWithPath("email").description("Email"),
                                fieldWithPath("phone").description("Phone"),
                                fieldWithPath("username").description("User Name"),
                                fieldWithPath("password").description("Password"),
                                fieldWithPath("acl").description("ACL")
                        )));
    }

    @Test
    @WithMockUser(value = "admin", roles = "ADMIN")
    void updateUser() throws Exception {
        AppUser user = getValidUser();
        String courseJson = objectMapper.writeValueAsString(user);

        ConstrainedFields fields = new ConstrainedFields(AppUser.class);

        mockMvc.perform(put("/api/v1/users/{id}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(courseJson))
                .andExpect(status().isNoContent())
                .andDo(document("v1/users-update",
                        pathParameters(
                                parameterWithName("id").description("UUID string of desired user to update.")
                        ),
                        requestFields(
                                fieldWithPath("id").description("User ID"),
                                fieldWithPath("firstname").description("First Name"),
                                fieldWithPath("lastname").description("Last Name"),
                                fieldWithPath("birthday").description("Birthday"),
                                fieldWithPath("email").description("Email"),
                                fieldWithPath("phone").description("Phone"),
                                fieldWithPath("username").description("User Name"),
                                fieldWithPath("password").description("Password"),
                                fieldWithPath("acl").description("ACL")
                        )));
    }

    @Test
    @WithMockUser(value = "admin", roles = "ADMIN")
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/user/{id}", UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("v1/users-delete",
                        pathParameters(
                                parameterWithName("id").description("UUID string of desired users to delete.")
                        )));
    }

    AppUser getValidUser() {
        return AppUser.builder()
                .firstname("Cheng")
                .lastname("Tao")
                .birthday(LocalDate.of(1986, 02, 12))
                .email("cheng.tao86@gmail.com")
                .phone("0760876779")
                .username("chengtao")
                .password("1111")
                .acl(List.of("ADMIN"))
                .build();
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". ")));
        }
    }
}
