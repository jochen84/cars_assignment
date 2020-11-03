package com.example.cars.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class AppUser implements Serializable {
    private static final long serialVersionUID = -4911994786915834478L;

    @Id
    private String id;  // for mongodb
    @NotEmpty(message = "Firstname can not be empty")
    @Size(min = 2, max = 14, message = "Firstname length not valid, must be between 2 and 14")
    private String firstname;
    @NotEmpty(message = "Lastname can not be empty")
    @Size(min = 2, max = 20, message = "Lastname length not valid, must be between 2 and 20")
    private String lastname;
    @PastOrPresent(message = "Birthday can not be present or in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate birthday;
    @Email(message = "E-mail address invalid")
    private String email;
    @Pattern(regexp = "([0-9]){2,4}-([0-9]){5,8}", message = "Phone number not valid")
    private String phone;
    @Size(min = 3, max = 12, message = "Username length invalid, must be between 3 and 12")
    @NotBlank(message = "Username must contain a value ")
    @Indexed(unique = true) //Not working?
    private String username;
    @Size(min = 4, max = 20, message = "Password length invalid, must be between 4 and 12")
    @NotBlank(message = "Password must contain a value ")
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private List<String> acl;

}
