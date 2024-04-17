package com.example.sakilaAPI.dto.input;

import com.example.sakilaAPI.Entities.Language;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class FilmInput {

    @NotNull(groups = {ValidationGroup.Create.class})
    @Size(min = 1, max = 128)
    private String title;

    @NotNull(groups = {ValidationGroup.Create.class})
    private Short languageId;

    @NotNull(groups = {ValidationGroup.Create.class})
    private Short duration;

    @NotNull(groups = {ValidationGroup.Create.class})
    @DecimalMax(value = "4.2", inclusive = true)
    private BigDecimal rate;

    @NotNull(groups = {ValidationGroup.Create.class})
    @DecimalMax(value = "5.2", inclusive = true)
    private BigDecimal cost;

    @NotNull(groups = {ValidationGroup.Create.class})
    @Size(min = 1, max = 500)
    private String description;

}
