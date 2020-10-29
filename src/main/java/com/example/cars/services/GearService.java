package com.example.cars.services;

import com.example.cars.entities.Gear;
import com.example.cars.repositories.GearRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GearService {

    private final GearRepository gearRepository;

    @Cacheable(value = "gearsCache")
    public List<Gear> findAll(){
        log.info("Request to find all users");
        return gearRepository.findAll();
    }

    @Cacheable(value = "gearsCache", key = "#id")
    public Gear findById(String id){
        return gearRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a gear with that id"));
    }

    @CachePut(value = "gearsCache", key = "#result.id")
    public Gear save(Gear gear){
        return gearRepository.save(gear);
    }

    @CachePut(value = "gearsCache", key = "#id")
    public void update(String id, Gear gear){
        if (!gearRepository.existsById(id)){
            log.error("Could not find a gear with that id");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a gear with that id");
        }
        gear.setId(id);
        gearRepository.save(gear);
    }
    @CacheEvict(value = "gearsCache", key = "#id")
    public void delete(String id){
        if (!gearRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a gear with that id");
        }
        gearRepository.deleteById(id);
    }

}








