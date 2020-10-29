package com.example.cars.services;

import com.example.cars.entities.Car;
import com.example.cars.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> findAll(){
        return carRepository.findAll();
    }

    public Car findById(String id){
        return carRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a car with that id"));
    }

    public Car save(Car car){
        return carRepository.save(car);
    }

    public void update(String id, Car car){
        if (!carRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a car with that id");
        }
        car.setId(id);
        carRepository.save(car);
    }

    public void delete(String id){
        if (!carRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a car with that id");
        }
        carRepository.deleteById(id);
    }

}
