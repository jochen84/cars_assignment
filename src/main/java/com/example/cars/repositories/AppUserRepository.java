package com.example.cars.repositories;

import com.example.cars.entities.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUser, String> {

    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByPhone(String phone);

}
