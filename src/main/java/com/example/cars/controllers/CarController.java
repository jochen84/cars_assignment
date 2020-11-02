package com.example.cars.controllers;

import com.example.cars.entities.Car;
import com.example.cars.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Secured({"ROLE_ADMIN", "ROLE_CARDEALER"})
    @GetMapping
//    public ResponseEntity<List<Car>> findAll(){
//        return ResponseEntity.ok(carService.findAll());
//    }
    public ResponseEntity<List<Car>> findAll(@RequestParam(required = false) String regNum, @RequestParam(required = false) String brand, @RequestParam(required = false) String model, @RequestParam(required = false) String color, @RequestParam(required = false) String prodYear, @RequestParam(required = false) String numOfSeats, @RequestParam(required = false) String equipment, @RequestParam(required = false) String fuel, @RequestParam(required = false) String isSupercharged, @RequestParam(required = false) String enginePosition, @RequestParam(required = false) String cylinders, @RequestParam(required = false) String gearBox, @RequestParam(required = false) String totalGears, @RequestParam(required = false) String driveLine,
                                             @RequestParam(required = false) boolean sortByRegNum, @RequestParam(required = false) boolean sortByBrand, @RequestParam(required = false) boolean sortByModel, @RequestParam(required = false) boolean sortByColor, @RequestParam(required = false) boolean sortByProdYear, @RequestParam(required = false) boolean sortByNumOfSeats){
        return ResponseEntity.ok(carService.findAll(regNum, brand, model, color, prodYear, numOfSeats, equipment, fuel, isSupercharged, enginePosition, cylinders, gearBox, totalGears, driveLine, sortByRegNum, sortByBrand, sortByModel, sortByColor, sortByProdYear, sortByNumOfSeats));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CARDEALER"})
    @GetMapping("/{id}")
    public ResponseEntity<Car> findById(@PathVariable String id){
        return ResponseEntity.ok(carService.findById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CARDEALER"})
    @PostMapping
    public ResponseEntity<Car> save(@Validated @RequestBody Car car){
        return ResponseEntity.ok(carService.save(car));
    }

    @Secured({"ROLE_ADMIN", "ROLE_CARDEALER"})
    @PutMapping("/{id}")
    public void update(@PathVariable String id, @Validated @RequestBody Car car){
        carService.update(id, car);
    }

    @Secured({"ROLE_ADMIN", "ROLE_CARDEALER"})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        carService.delete(id);
    }
}
