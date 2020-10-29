package com.example.cars.repositories;

import com.example.cars.entities.Gear;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GearRepository extends MongoRepository<Gear, String> {
}
