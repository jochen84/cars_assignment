package com.example.cars.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class GearBox {
    @Id
    private String id;
    @NotEmpty(message = "Gearbox can not be empty")
    @Pattern(regexp = "AUT|MAN", message = "Gearbox must be AUT or MAN")
    private String gearBox;
    @Min(value = 4, message = "Total Gears should be no smaller than 4")
    @Max(value = 8, message = "Total Gears should be no larger than 8")
    private int totalGears;
    @NotEmpty(message = "Drive line can not be empty")
    @Pattern(regexp = "FRONT|REAR|AWD", message = "Drive Line must be FRONT or REAR or AWD ")
    private String driveLine;
}
