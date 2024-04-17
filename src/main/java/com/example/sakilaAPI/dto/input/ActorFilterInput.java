package com.example.sakilaAPI.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data //Adds the getters and setters
public class ActorFilterInput {

    private int page = 0;
    private int size = 10;

}


