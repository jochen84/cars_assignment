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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EngineService {
    private final EngineRepository engineRepository;

    @Cacheable(value = "enginesCache")
    public List<Engine> findAll(String fuel, String isSupercharged, String enginePosition, String cylinders, boolean sortByFuel, boolean sortByIsSupercharged, boolean sortByEnginePosition, boolean sortByCylinders){
        log.info("Request to find all engines");
        var engines = engineRepository.findAll();
        if(fuel!=null){
            engines = engines.stream().filter(engine -> engine.getFuel().equalsIgnoreCase(fuel)).collect(Collectors.toList());
        }
        if(isSupercharged!=null){
            engines = engines.stream().filter(engine -> Boolean.toString(engine.isSupercharged()).equalsIgnoreCase(isSupercharged)).collect(Collectors.toList());
        }if(enginePosition!=null){
            engines = engines.stream().filter(engine -> engine.getEnginePosition().equalsIgnoreCase(enginePosition)).collect(Collectors.toList());
        }
        if(cylinders!=null){
            engines = engines.stream().filter(engine -> Integer.toString(engine.getCylinders()).equals(cylinders)).collect(Collectors.toList());
        }
        if(sortByFuel){
            engines.sort(Comparator.comparing(Engine::getFuel));
        }
        if(sortByIsSupercharged){
            engines.sort(Comparator.comparing(Engine::isSupercharged));
        }
        if(sortByEnginePosition){
            engines.sort(Comparator.comparing(Engine::getEnginePosition));
        }
        if(sortByCylinders){
            engines.sort(Comparator.comparing(Engine::getCylinders));
        }
        return engines;
    }

    @Cacheable(value = "enginesCache")
    public Engine findById(String id){
        return engineRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an engine with that id"));
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