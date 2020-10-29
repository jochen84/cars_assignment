package com.example.cars.repositories;

import com.example.cars.entities.GearBox;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GearBoxRepository extends MongoRepository<GearBox, String> {
}
