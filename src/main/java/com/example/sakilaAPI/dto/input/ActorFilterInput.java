package com.example.sakilaAPI.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data //Adds the getters and setters
public class ActorFilterInput {

    @Size(min = 0, message = "Page number must be positive or zero")
    private int page = 0;

    @Size(min = 0, message = "Page size must be positive or zero")
    private int size = 10;

}


