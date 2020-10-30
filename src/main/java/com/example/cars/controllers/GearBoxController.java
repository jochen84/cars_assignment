package com.example.cars.controllers;

import com.example.cars.entities.GearBox;
import com.example.cars.services.GearBoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController //REST API Endpoints
@RequestMapping("/api/v1/gearboxes")
public class GearBoxController {

    @Autowired
    private GearBoxService gearBoxService;

    @GetMapping
    public ResponseEntity<List<GearBox>> findAll(@RequestParam(required = false) String name, @RequestParam(required = false) boolean sort) {
        return ResponseEntity.ok(gearBoxService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity <GearBox> findById(@PathVariable String id) {
        return ResponseEntity.ok(gearBoxService.findById(id));
    }

    @PostMapping
    public ResponseEntity<GearBox> save(@Validated @RequestBody GearBox gearBox) {
        return ResponseEntity.ok(gearBoxService.save(gearBox));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated @PathVariable String id, @RequestBody GearBox gearBox) {
        gearBoxService.update(id, gearBox);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        gearBoxService.delete(id);
    }

}
