package com.example.sakilaAPI.dto.output;

import com.example.sakilaAPI.Entities.Film;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilmSimpleOutput {
    private Short id;
    private String title;
    private String description;

    public static FilmSimpleOutput from(Film film) {
        return new FilmSimpleOutput(film.getId(), film.getTitle(), film.getDescription());
    }
}