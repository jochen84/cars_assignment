package com.example.cars.repositories;

import com.example.cars.entities.Engine;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EngineRepository extends MongoRepository<Engine, String> {
    

}
