package com.example.sakilaAPI.dto.input;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FilmInput {

    @NotNull(groups = {ValidationGroup.Create.class}, message = "Title cannot be null")
    @Size(min = 1, max = 128, message = "Title must be between 1 and 128 characters")
    private String title;

    @NotNull(groups = {ValidationGroup.Create.class}, message = "Duration cannot be null")
    private Short duration;

    @NotNull(groups = {ValidationGroup.Create.class}, message = "Rate cannot be null")
    @DecimalMax(value = "4.2", inclusive = true, message = "Rate cannot be greater than 4.2")
    private BigDecimal rate;

    @NotNull(groups = {ValidationGroup.Create.class}, message = "Cost cannot be null")
    @DecimalMax(value = "5.2", inclusive = true, message = "Cost cannot be greater than 5.2")
    private BigDecimal cost;

    @NotNull(groups = {ValidationGroup.Create.class}, message = "Description cannot be null")
    @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
    private String description;

    @NotNull(groups = {ValidationGroup.Create.class}, message = "Language ID cannot be null")
    private Long languageId;

}
