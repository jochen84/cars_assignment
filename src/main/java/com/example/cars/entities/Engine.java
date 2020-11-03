package com.example.cars.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Data
public class Engine {
    @Id
    private String id;
    @NotEmpty(message = "Fuel can not be empty")
    @Pattern(regexp = "PETROL|DIESEL|ELECTRIC|HYBRID", message = "Fuel must be PETROL, DIESEL, ELECTRIC or HYBRID.")
    private String fuel;
    private boolean isSupercharged;
    @NotEmpty(message = "Engine Position can not be empty")
    @Pattern(regexp = "FRONT|REAR", message = "Engine Position must be Front or Rear")
    private String enginePosition;
    @Min(value = 1, message = "Cylinders must be no smaller than 1.")
    @Max(value = 16, message = "Cylinders must be no larger than 16.")
    private int cylinders;
}
