package com.example.sakilaAPI.dto.output;

import com.example.sakilaAPI.Entities.Film;
import com.example.sakilaAPI.Entities.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class FilmOutput {
    private Short id;
    private String title;
    private Short duration;
    private BigDecimal rate;
    private BigDecimal cost;
    private String description;
    private Short languageId;
    private String languageName;
    private List<ActorReferenceOutput> cast;

    public static FilmOutput from(Film film) {
        return new FilmOutput(
                film.getId(),
                film.getTitle(),
                film.getDuration(),
                film.getRate(),
                film.getCost(),
                film.getDescription(),
                film.getLanguage().getId(),
                film.getLanguage().getName(),
                film.getCast().
                        stream().
                        map(ActorReferenceOutput::from).
                        collect(Collectors.toList()));
    }
}
