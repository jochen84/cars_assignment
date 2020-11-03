package com.example.cars.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class CarRestricted implements Serializable {
    private static final long serialVersionUID = 7478791537814469151L;

    @NotBlank(message = "Brand can not be empty")
    @Size(min = 2, max = 16, message = "Brand length not valid, must be between 2 and 16 letters")
    private String brand;
    @NotBlank(message = "Model can not be empty")
    @Size(min = 1, max = 16, message = "Model length not valid, must be between 1 and 16 letters")
    private String model;
    @Min(value = 1800, message = "Production Year should be larger than 1800")
    @Max(value = 2020, message = "Production Year should be smaller than 2020")
    private int prodYear;
    @PositiveOrZero
    private int price;
}
