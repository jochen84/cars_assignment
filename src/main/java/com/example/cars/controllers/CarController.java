package com.example.cars.controllers;

import com.example.cars.entities.Car;
import com.example.cars.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> findAll(){
        return ResponseEntity.ok(carService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> findById(@PathVariable String id){
        return ResponseEntity.ok(carService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Car> save(@Validated @RequestBody Car car){
        return ResponseEntity.ok(carService.save(car));
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @Validated @RequestBody Car car){
        carService.update(id, car);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        carService.delete(id);
    }
}
