package com.example.cars.controllers;

import com.example.cars.entities.AppUser;
import com.example.cars.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public ResponseEntity<List<AppUser>> findAllUsers(){
        return ResponseEntity.ok(appUserService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> findUserById(@PathVariable String id){
        return ResponseEntity.ok(appUserService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AppUser> save(@Validated @RequestBody AppUser appUser){
        return ResponseEntity.ok(appUserService.save(appUser));
    }

    @PutMapping("/{id}")
    public void userUpdate(@PathVariable String id, @Validated @RequestBody AppUser appUser){
        appUserService.update(id, appUser);
    }

    @DeleteMapping("/{id}")
    public void userDelete(@PathVariable String id){
        appUserService.delete(id);
    }
}
