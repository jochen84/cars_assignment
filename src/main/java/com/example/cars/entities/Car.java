package com.example.cars.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;


@Data
public class Car implements Serializable {
    private static final long serialVersionUID = 7478791537814469150L;

    @Id
    private String id;
    @NotBlank(message = "Registration no. can not be empty")
    @Pattern(regexp = "([A-Z]){3}([0-9]){2}([A-Z0-9])", message = "Registration number is not valid")
    private String regNum;
    @NotBlank(message = "Brand can not be empty")
    @Size(min = 2, max = 16, message = "Brand length not valid, must be between 2 and 16 letters")
    private String brand;
    @NotBlank(message = "Model can not be empty")
    @Size(min = 1, max = 16, message = "Model length not valid, must be between 1 and 16 letters")
    private String model;
    @NotBlank(message = "Color can not be empty")
    @Size(min = 2, max = 16, message = "Color length not valid, must be between 2 and 16 letters")
    private String color;
    @Min(value = 1800, message = "Production Year should be larger than 1800")
    @Max(value = 2020, message = "Production Year should be smaller than 2020")
    private int prodYear;
    @Min(value = 2, message = "No. of Seats should be no smaller than 2")
    @Max(value = 9, message = "No. of Seats should be no larger than 9")
    private int numOfSeats;
    @PositiveOrZero
    private int price;
    private List<String> equipment;
    @NotNull
    @DBRef
    private Engine engine;
    @NotNull
    @DBRef
    private GearBox gearBox;
    @NotNull
    @Pattern(regexp = "Instock|Reserved|Sold", message = "Available strings are Instock|Reserved|Sold")
    private String status;
    private AppUser reservedByAppUser;
}
