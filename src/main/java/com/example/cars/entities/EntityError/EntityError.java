package com.example.cars.entities.EntityError;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntityError {
    private String field;
    private String message;
    private String rejectedValue;
}
