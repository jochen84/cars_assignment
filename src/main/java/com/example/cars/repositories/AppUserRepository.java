package com.example.cars.repositories;

import com.example.cars.entities.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUserRepository extends MongoRepository<AppUser, String> {
}
