package com.example.cars.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.*;

@Data
public class GearBox {
    @Id
    private String id;
    @NotEmpty(message = "Gearbox can not be empty")
    @Pattern(regexp = "Auto|Manual", message = "Gearbox must be Auto or Manual")
    private String gearBox;
    @Min(value = 4, message = "Total Gears should be no smaller than 4")
    @Max(value = 8, message = "Total Gears should be no larger than 8")
    private int totalGears;
    @NotEmpty(message = "Drive line can not be empty")
    @Pattern(regexp = "Front|Rear|AWD", message = "Drive Line must be Front or Rear or AWD ")
    private String driveLine;
}
