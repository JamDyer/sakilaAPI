package com.example.sakilaAPI.Controllers;

import com.example.sakilaAPI.Entities.Film;
import com.example.sakilaAPI.Entities.Language;
import com.example.sakilaAPI.REPOSITORIES.FilmRepository;
import com.example.sakilaAPI.dto.input.FilmInput;
import com.example.sakilaAPI.dto.output.FilmOutput;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("/{id}")
    public ResponseEntity<FilmOutput> getFilmById(@PathVariable Short id) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        if (filmOptional.isPresent()) {
            Film film = filmOptional.get();
            FilmOutput filmOutput = FilmOutput.from(film);

            // HATEOAS stuff - links
            EntityModel<FilmOutput> entityModel = EntityModel.of(filmOutput);
            Link selfLink = WebMvcLinkBuilder.linkTo(FilmController.class).slash(id).withSelfRel();
            entityModel.add(selfLink);

            return ResponseEntity.ok(entityModel.getContent());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<FilmOutput>>> getAllFilms() {
        List<Film> films = filmRepository.findAll();
        List<EntityModel<FilmOutput>> filmOutputs = films.stream()
                .map(film -> {
                    EntityModel<FilmOutput> entityModel = EntityModel.of(FilmOutput.from(film));
                    Link selfLink = WebMvcLinkBuilder.linkTo(FilmController.class).slash(film.getId()).withSelfRel();
                    entityModel.add(selfLink);
                    return entityModel;
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<FilmOutput>> collectionModel = CollectionModel.of(filmOutputs);
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmOutput createFilm(@Valid @RequestBody FilmInput filmInput) {
        Film film = new Film();
        film.setTitle(filmInput.getTitle());
        film.setDuration(filmInput.getDuration());
        film.setRate(filmInput.getRate());
        film.setCost(filmInput.getCost());
        film.setDescription(filmInput.getDescription());
        film.setLanguage(filmInput.getLanguageId());


        final var saved = filmRepository.save(film);
        return FilmOutput.from(saved);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilmById(@PathVariable Short id) {
        filmRepository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FilmOutput> partialUpdateFilmById(@PathVariable Short id, @RequestBody FilmInput filmInput) {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        if (optionalFilm.isPresent()) {
            Film film = optionalFilm.get();
            if (filmInput.getTitle() != null) {
                film.setTitle(filmInput.getTitle());
            }
            if (filmInput.getDuration() != null) {
                film.setDuration(filmInput.getDuration());
            }
            if (filmInput.getRate() != null) {
                film.setRate(filmInput.getRate());
            }
            if (filmInput.getCost() != null) {
                film.setCost(filmInput.getCost());
            }
            if (filmInput.getDescription() != null) {
                film.setDescription(filmInput.getDescription());
            }
            if (filmInput.getLanguageId() != null) {
                film.setLanguage(filmInput.getLanguageId());
            }

            final var updatedFilm = filmRepository.save(film);
            FilmOutput filmOutput = FilmOutput.from(updatedFilm);
            return ResponseEntity.ok(filmOutput);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}