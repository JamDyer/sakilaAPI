// ActorOutput.java
package com.example.sakilaAPI.dto.output;

import com.example.sakilaAPI.Entities.Actor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ActorOutput {
    private Short id;
    private String firstName;
    private String lastName;
    private List<FilmReferenceOutput> films;

    public static ActorOutput from(Actor actor) {
        return new ActorOutput(actor.getId(),
                actor.getFirstName(),
                actor.getLastName(),
                actor.getFilms().
                        stream().
                        map(FilmReferenceOutput::from).
                        collect(Collectors.toList()));
    }
}