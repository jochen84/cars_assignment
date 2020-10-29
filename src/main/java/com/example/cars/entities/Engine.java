package com.example.cars.entities;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;

public class Engine {
    @Id
    private String id;
    @NotEmpty(message = "Fuel can not be empty")
    @Pattern(regexp = "Petrol|Diesel|Electric|Hybrid", message = "Fuel must be Petrol, Diesel, Electric or Hybrid.")
    private String fuel;
    @NotEmpty(message = "Is Supercharged can not be empty")
    private boolean isSupercharged;
    @NotEmpty(message = "Engine Position can not be empty")
    @Pattern(regexp = "Front|Rear", message = "Engine Postion must be Front or Rear")
    private String enginePosition;
    @NotEmpty(message = "Cylinders can not be empty")
    @Min(value = 1, message = "Cylinders must be no smaller than 1.")
    @Max(value = 16, message = "Cylinders must be no larger than 16.")
    private int cylinders;
}
