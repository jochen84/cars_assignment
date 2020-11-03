package com.example.cars.controllers;

import com.example.cars.entities.AppUser;
import com.example.cars.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @Secured({"ROLE_ADMIN", "ROLE_CARDEALER"})
    @GetMapping
    public ResponseEntity<List<AppUser>> findAllUsers(@RequestParam(required = false) String name, @RequestParam(required = false) String mail, @RequestParam(required = false) boolean sortByName){
        return ResponseEntity.ok(appUserService.findAll(name, mail, sortByName));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CARDEALER", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> findUserById(@PathVariable String id){
        return ResponseEntity.ok(appUserService.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<AppUser> save(@Validated @RequestBody AppUser appUser){
        return ResponseEntity.ok(appUserService.save(appUser));
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userUpdate(@PathVariable String id, @Validated @RequestBody AppUser appUser){
        appUserService.update(id, appUser);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void userDelete(@PathVariable String id){
        appUserService.delete(id);
    }
}
