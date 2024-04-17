package com.example.sakilaAPI.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data // getter, setter, default, constructor, hash function, to string - all in one
public class ActorInput {
    @NotNull(groups = {ValidationGroup.Create.class})
    @Size(min = 1, max = 45)
    private String firstName;
    @NotNull(groups = {ValidationGroup.Create.class})
    @Size(min = 1, max = 45)
    private String lastName;

}
