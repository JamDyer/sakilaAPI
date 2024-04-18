package com.example.sakilaAPI.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data // getter, setter, default, constructor, hash function, to string - all in one
public class ActorInput {

    @NotNull(groups = {ValidationGroup.Create.class}, message = "First name cannot be null")
    @Size(min = 1, max = 45, message = "First name must be between 1 and 45 characters")
    private String firstName;

    @NotNull(groups = {ValidationGroup.Create.class}, message = "Last name cannot be null")
    @Size(min = 1, max = 45, message = "Last name must be between 1 and 45 characters")
    private String lastName;

}
