package com.example.cars.entities;

import lombok.Builder;
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
    @Pattern(regexp = "Auto|Manual", message = "Gearbox must be Auto or Manual")
    private String gearBox;
    @NotEmpty(message = "Total Gears can not be empty")
    @Min(value = 4, message = "Total Gears should be no smaller than 4")
    @Max(value = 8, message = "Total Gears should be no larger than 8")
    private int totalGears;
    @NotEmpty(message = "Drive line can not be empty")
    @Pattern(regexp = "Front|Rear|AWD", message = "Drive Line must be Front or Rear or AWD ")
    private String driveLine;
}
