package com.example.sakilaAPI.dto.output;


import com.example.sakilaAPI.Entities.Film;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilmReferenceOutput {
    private Short id;
    private String title;
    private String description;

    public static FilmReferenceOutput from(Film film) {
        return new FilmReferenceOutput(film.getId(), film.getTitle(), film.getDescription());
    }

}
