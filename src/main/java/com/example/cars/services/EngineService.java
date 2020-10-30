package com.example.cars.services;

import com.example.cars.entities.Engine;
import com.example.cars.repositories.EngineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EngineService {
    private final EngineRepository engineRepository;
    @Cacheable(value = "enginesCache")
    public List<Engine> findAll(){
        return engineRepository.findAll();
    }
    @Cacheable(value = "enginesCache")
    public Engine findById(String id){
        return engineRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an engine with that id"));
    }
    @Cacheable(value = "enginesCache")
    public List<Engine> findByFuel(String fuel){
        return engineRepository.findByFuel(fuel)
                .stream().collect(Collectors.toList());
    }
    @Cacheable(value = "enginesCache")
    public List<Engine> findByIsSupercharged(boolean isSupercharged){
        return engineRepository.findByIsSupercharged(isSupercharged)
                .stream().collect(Collectors.toList());
    }
    @Cacheable(value = "enginesCache")
    public List<Engine> findByEnginePosition(String enginePosition){
        return engineRepository.findByEnginePosition(enginePosition)
                .stream().collect(Collectors.toList());
    }
    @Cacheable(value = "enginesCache")
    public List<Engine> findByCylinders(int cylinders){
        return engineRepository.findByCylinders(cylinders)
                .stream().collect(Collectors.toList());
    }
    @CachePut(value = "enginesCache", key = "#result.id")
    public Engine save(Engine engine){
        return engineRepository.save(engine);
    }
    @CachePut(value = "enginesCache", key = "#id")
    public void update(String id, Engine engine){
        if(!engineRepository.existsById(id)){
            log.error("Could not find an engine with that id");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an engine with that id");
        }
        engine.setId(id);
        engineRepository.save(engine);
    }
    @CacheEvict(value = "enginesCache", key = "#id")
    public void delete(String id){
        if(!engineRepository.existsById(id)){
            log.error("Could not find an engine with that id");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an engine with that id");
        }
        engineRepository.deleteById(id);
    }
}