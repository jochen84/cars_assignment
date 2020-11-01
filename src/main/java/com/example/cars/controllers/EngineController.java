package com.example.cars.controllers;


import com.example.cars.entities.Engine;
import com.example.cars.services.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/engines")
public class EngineController {

    @Autowired
    private EngineService engineService;

    @GetMapping
    public ResponseEntity<List<Engine>> findAllEngine(@RequestParam(required = false) String fuel, @RequestParam(required = false) String isSupercharged, @RequestParam(required = false)String enginePosition, @RequestParam(required = false)String cylinders, @RequestParam(required = false)boolean sortByFuel, @RequestParam(required = false)boolean sortByIsSupercharged, @RequestParam(required = false)boolean sortByEnginePosition, @RequestParam(required = false)boolean sortByCylinders){
        return ResponseEntity.ok(engineService.findAll(fuel, isSupercharged, enginePosition, cylinders, sortByFuel, sortByIsSupercharged, sortByEnginePosition, sortByCylinders));
    }
//    public ResponseEntity<List<Engine>> findAllEngine(){
//        return ResponseEntity.ok(engineService.findAll());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Engine> findById(@PathVariable String id){
        return ResponseEntity.ok(engineService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Engine> save(@Validated @RequestBody Engine engine){
        return ResponseEntity.ok(engineService.save(engine));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @Validated @RequestBody Engine engine){
        engineService.update(id, engine);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id){
        engineService.delete(id);
    }

}
