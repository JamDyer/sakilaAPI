package com.example.sakilaAPI.Controllers;

import com.example.sakilaAPI.Entities.Film;
import com.example.sakilaAPI.Entities.Language;
import com.example.sakilaAPI.REPOSITORIES.FilmRepository;
import com.example.sakilaAPI.REPOSITORIES.LanguageRepository;
import com.example.sakilaAPI.dto.input.FilmInput;
import com.example.sakilaAPI.dto.input.ValidationGroup;
import com.example.sakilaAPI.dto.output.FilmOutput;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @GetMapping
    public List<FilmOutput> readAll() {
        final var films = filmRepository.findAll();
        return films.stream()
                .map(FilmOutput::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmOutput> getFilmById(@PathVariable Short id) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        if (filmOptional.isPresent()) {
            Film film = filmOptional.get();
            FilmOutput filmOutput = FilmOutput.from(film);
            return ResponseEntity.ok(filmOutput);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmOutput create(@Validated(ValidationGroup.Create.class) @RequestBody FilmInput data) {
        final var film = new Film();
        film.setTitle(data.getTitle());
        film.setDuration(data.getDuration());
        film.setRate(data.getRate());
        film.setCost(data.getCost());
        film.setDescription(data.getDescription());

        Optional<Language> languageOptional = languageRepository.findById(data.getLanguageId());
        if (languageOptional.isEmpty()) {
            throw new RuntimeException("Language with ID " + data.getLanguageId() + " not found.");
        }
        Language language = languageOptional.get();
        film.setLanguage(language);

        final var saved = filmRepository.save(film);
        return FilmOutput.from(saved);
    }

    @PatchMapping("/{id}")
    public FilmOutput partialUpdateFilm(@PathVariable Short id,
                                        @Validated(ValidationGroup.Update.class) @RequestBody FilmInput data) {
        return filmRepository.findById(id)
                .map(film -> {
                    if (data.getTitle() != null) {
                        film.setTitle(data.getTitle());
                    }
                    if (data.getLanguageId() != null) {
                        Optional<Language> languageOptional = languageRepository.findById(data.getLanguageId());
                        if (languageOptional.isEmpty()) {
                            throw new EntityNotFoundException("Language with ID " + data.getLanguageId() + " not found.");
                        }
                        Language language = languageOptional.get();
                        film.setLanguage(language);
                    }
                    if (data.getDuration() != null) {
                        film.setDuration(data.getDuration());
                    }
                    if (data.getRate() != null) {
                        film.setRate(data.getRate());
                    }
                    if (data.getCost() != null) {
                        film.setCost(data.getCost());
                    }
                    if (data.getDescription() != null) {
                        film.setDescription(data.getDescription());
                    }
                    final var saved = filmRepository.save(film);
                    return FilmOutput.from(saved);
                })
                .orElseThrow(() -> new EntityNotFoundException("Film not found with id " + id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilm(@PathVariable Short id) {
        filmRepository.deleteById(id);
    }

}
