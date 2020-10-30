package com.example.cars.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;


@Data
public class Car implements Serializable {
    private static final long serialVersionUID = 7478791537814469150L;

    @Id
    private String id;
    @NotEmpty(message = "Registration no. can not be empty")
    @Size(min = 2, max = 7, message = "Registration no. length not valid")
    private String regNum;
    @NotEmpty(message = "Brand can not be empty")
    @Size(min = 1, max = 15, message = "Brand length not valid")
    private String brand;
    @NotEmpty(message = "Model can not be empty")
    @Size(min = 1, max = 10, message = "Model length not valid")
    private String model;
    @NotEmpty(message = "Color can not be empty")
    @Size(min = 1, max = 10, message = "Color length not valid")
    private String color;
    @NotNull(message = "Production Year can not be empty")
    @Min(value = 1800, message = "Production Year should be larger than 1800")
    @Max(value = 2020, message = "Production Year should be smaller than 2020")
    private int prodYear;
    @NotNull(message = "No. of Seats can not be empty")
    @Min(value = 2, message = "No. of Seats should be no smaller than 2")
    @Max(value = 7, message = "No. of Seats should be no larger than 7")
    private int numOfSeats;
    private List<String> equipment;
    private Engine engine;
    private GearBox gearBox;
}
