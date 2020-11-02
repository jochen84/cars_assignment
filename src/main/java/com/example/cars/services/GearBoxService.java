package com.example.cars.services;

import com.example.cars.entities.Car;
import com.example.cars.entities.GearBox;
import com.example.cars.repositories.GearBoxRepository;
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
public class GearBoxService {

    private final GearBoxRepository gearBoxRepository;

    @Cacheable(value = "gearsCache")
/*    public List<GearBox> findAll(){
        log.info("Request to find all gearboxes");
        return gearBoxRepository.findAll();
    }*/
    public List<GearBox> findAll(String gearBox, String totalGears, String driveLine, boolean sortByGearBox, boolean sortByTotalGears, boolean sortByDriveLine){
        log.info("Request to find all gearboxes");
        var gearboxes = gearBoxRepository.findAll();
        if(gearBox!=null){
            gearboxes = gearboxes.stream().filter(gearbox -> gearbox.getGearBox().equalsIgnoreCase(gearBox)).collect(Collectors.toList());
        }
        if(totalGears!=null){
            gearboxes = gearboxes.stream().filter(gearbox -> Integer.toString(gearbox.getTotalGears()).equals(totalGears)).collect(Collectors.toList());
        }
        if(driveLine!=null){
            gearboxes = gearboxes.stream().filter(gearbox -> gearbox.getDriveLine().equalsIgnoreCase(driveLine)).collect(Collectors.toList());
        }
        if(sortByGearBox){
            gearboxes.sort(Comparator.comparing(GearBox::getGearBox));
        }
        if(sortByTotalGears){
            gearboxes.sort(Comparator.comparing(GearBox::getTotalGears));
        }
        if(sortByDriveLine){
            gearboxes.sort(Comparator.comparing(GearBox::getDriveLine));
        }
        return gearboxes;
    }

    @Cacheable(value = "gearsCache", key = "#id")
    public GearBox findById(String id){
        return gearBoxRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a gearbox with that id"));
    }

    @CachePut(value = "gearsCache", key = "#result.id")
    public GearBox save(GearBox gearBox){
        return gearBoxRepository.save(gearBox);
    }

    @CachePut(value = "gearsCache", key = "#id")
    public void update(String id, GearBox gearBox){
        if (!gearBoxRepository.existsById(id)){
            log.error("Could not find a gearbox with that id");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a gearbox with that id");
        }
        gearBox.setId(id);
        gearBoxRepository.save(gearBox);
    }
    @CacheEvict(value = "gearsCache", key = "#id")
    public void delete(String id){
        if (!gearBoxRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a gearbox with that id");
        }
        gearBoxRepository.deleteById(id);
    }

}








