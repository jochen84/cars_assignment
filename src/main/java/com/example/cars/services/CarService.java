package com.example.cars.services;

import com.example.cars.entities.Car;
import com.example.cars.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final AppUserService appUserService;

    @Cacheable(value = "carsCache")
    public List<Car> findAll(String regNum, String brand, String model, String color, String prodYear, String numOfSeats, String equipment, String fuel, String isSupercharged, String enginePosition, String cylinders, String gearBox, String totalGears, String driveLine, String status,
                             boolean sortByRegNum, boolean sortByBrand, boolean sortByModel, boolean sortByColor, boolean sortByProdYear, boolean sortByNumOfSeats, boolean sortByStatus){
        log.info("Request to find all cars");
        var cars = carRepository.findAll();
        if(regNum!=null){
            cars = cars.stream().filter(car -> car.getRegNum().equalsIgnoreCase(regNum)).collect(Collectors.toList());
        }
        if(brand!=null){
            cars = cars.stream().filter(car -> car.getBrand().equalsIgnoreCase(brand)).collect(Collectors.toList());
        }
        if(model!=null){
            cars = cars.stream().filter(car -> car.getModel().equalsIgnoreCase(model)).collect(Collectors.toList());
        }
        if(color!=null){
            cars = cars.stream().filter(car -> car.getColor().equalsIgnoreCase(color)).collect(Collectors.toList());
        }
        if(prodYear!=null){
            cars = cars.stream().filter(car -> Integer.toString(car.getProdYear()).equals(prodYear)).collect(Collectors.toList());
        }
        if(numOfSeats!=null){
            cars = cars.stream().filter(car -> Integer.toString(car.getNumOfSeats()).equals(numOfSeats)).collect(Collectors.toList());
        }
        if(equipment!=null){
            cars = cars.stream().filter(car -> car.getEquipment().contains(equipment)).collect(Collectors.toList());//***
        }
        if(fuel!=null){
            cars = cars.stream().filter(car -> car.getEngine()!=null && car.getEngine().getFuel().equalsIgnoreCase(fuel)).collect(Collectors.toList());
        }
        if(isSupercharged!=null){
            cars = cars.stream().filter(car -> car.getEngine()!=null && Boolean.toString(car.getEngine().isSupercharged()).equalsIgnoreCase(isSupercharged)).collect(Collectors.toList());
        }
        if(enginePosition!=null){
            cars = cars.stream().filter(car -> car.getEngine()!=null && car.getEngine().getEnginePosition().equalsIgnoreCase(enginePosition)).collect(Collectors.toList());
        }
        if(cylinders!=null){
            cars = cars.stream().filter(car -> car.getEngine()!=null && Integer.toString(car.getEngine().getCylinders()).equals(cylinders)).collect(Collectors.toList());
        }
        if(gearBox!=null){
            cars = cars.stream().filter(car -> car.getGearBox()!=null && car.getGearBox().getGearBox().equalsIgnoreCase(gearBox)).collect(Collectors.toList());
        }
        if(totalGears!=null){
            cars = cars.stream().filter(car -> car.getGearBox()!=null && Integer.toString(car.getGearBox().getTotalGears()).equals(totalGears)).collect(Collectors.toList());
        }
        if(driveLine!=null){
            cars = cars.stream().filter(car -> car.getGearBox()!=null && car.getGearBox().getDriveLine().equalsIgnoreCase(driveLine)).collect(Collectors.toList());
        }
        if(status!=null){
            cars = cars.stream().filter(car -> car.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
        }
        if(sortByRegNum){
            cars.sort(Comparator.comparing(Car::getRegNum));
        }
        if(sortByBrand){
            cars.sort(Comparator.comparing(Car::getBrand));
        }
        if(sortByModel){
            cars.sort(Comparator.comparing(Car::getModel));
        }
        if(sortByColor){
            cars.sort(Comparator.comparing(Car::getColor));
        }
        if(sortByProdYear){
            cars.sort(Comparator.comparing(Car::getProdYear));
        }
        if(sortByNumOfSeats){
            cars.sort(Comparator.comparing(Car::getNumOfSeats));
        }
        if(sortByStatus){
            cars.sort(Comparator.comparing(Car::getStatus));
        }
        return cars;
    }

    @Cacheable(value = "carsCache", key = "#id")
    public Car findById(String id){
        return carRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a car with that id"));
    }

    @CachePut(value = "carsCache", key = "#result.id")
    public Car save(Car car){
        return carRepository.save(car);
    }

    @CachePut(value = "carsCache", key = "#id")
    public void update(String id, Car car){
        if (!carRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a car with that id");
        }
        car.setId(id);
        carRepository.save(car);
    }

    @CacheEvict(value = "carsCache", key = "#id")
    public void delete(String id){
        if (!carRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a car with that id");
        }
        carRepository.deleteById(id);
    }

    @Cacheable(value = "carsCache")
    public List<String> findAllRestricted(String brand, String model, String color, String prodYear, boolean sortByBrand, boolean sortByModel, boolean sortByColor, boolean sortByProdYear){
        List<String> restrictedCarList;
        var cars = carRepository.findAll();
        if(brand!=null){
            cars = cars.stream().filter(car -> car.getBrand().equalsIgnoreCase(brand)).collect(Collectors.toList());
        }
        if(model!=null){
            cars = cars.stream().filter(car -> car.getModel().equalsIgnoreCase(model)).collect(Collectors.toList());
        }
        if(color!=null){
            cars = cars.stream().filter(car -> car.getColor().equalsIgnoreCase(color)).collect(Collectors.toList());
        }
        if(prodYear!=null){
            cars = cars.stream().filter(car -> Integer.toString(car.getProdYear()).equals(prodYear)).collect(Collectors.toList());
        }
        if(sortByBrand){
            cars.sort(Comparator.comparing(Car::getBrand));
        }
        if(sortByModel){
            cars.sort(Comparator.comparing(Car::getModel));
        }
        if(sortByColor){
            cars.sort(Comparator.comparing(Car::getColor));
        }
        if(sortByProdYear){
            cars.sort(Comparator.comparing(Car::getProdYear));
        }
        restrictedCarList = cars.stream()
                .map(car -> "Brand: "+ car.getBrand()+"+ Model: "+car.getModel()+"+ Production Year: "+car.getProdYear()+"+ Price: "+car.getPrice()).collect(Collectors.toList());
        return restrictedCarList;
    }

    @CachePut(value = "carsCache", key = "#id")
    public void reserveCar(String id){
        //if (SecurityContextHolder.getContext().getAuthentication().getName() == "anonymousUser"){
        //    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You must log in to reserve a car");
        //}
        var currentUser = appUserService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        var car = findById(id);
        if (car.getStatus().equals("Reserved") || car.getStatus().equals("Sold")){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "That car is already reserved or sold");
        }
        car.setId(id);
        car.setStatus("Reserved");
        car.setReservedByAppUser(currentUser);
        update(id, car);
    }

    @CachePut(value = "carsCache", key = "#id")
    public void unReserveCar(String id){
        var car = findById(id);
        var currentReserveUser = car.getReservedByAppUser();
        var currentLoggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        var isSuperman = checkAuthority("ADMIN")||checkAuthority("CARDEALER");
        if (!car.getStatus().equals("Reserved")){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Car is not reserved at the moment");
        }
        if (!isSuperman && !currentReserveUser.getUsername().equals(currentLoggedInUser)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized to do this");
        }

        car.setId(id);
        car.setStatus("Instock");
        car.setReservedByAppUser(null);
        update(id, car);
    }

    private boolean checkAuthority(String role){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().toUpperCase().equals("ROLE_"+role));
    }
}
