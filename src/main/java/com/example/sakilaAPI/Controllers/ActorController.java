package com.example.sakilaAPI.Controllers;

import com.example.sakilaAPI.Entities.Actor;
import com.example.sakilaAPI.REPOSITORIES.ActorRepository;
import com.example.sakilaAPI.dto.input.ActorInput;
import com.example.sakilaAPI.dto.input.ValidationGroup;
import com.example.sakilaAPI.dto.output.ActorOutput;
import com.example.sakilaAPI.dto.output.ActorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ActorOutput> getActorById(@PathVariable Short id) {
        Optional<Actor> actorOptional = actorRepository.findById(id);
        if (actorOptional.isPresent()) {
            Actor actor = actorOptional.get();
            ActorOutput actorOutput = ActorOutput.from(actor);

            //HATEOAS stuff - links
            EntityModel<ActorOutput> entityModel = EntityModel.of(actorOutput);
            Link selfLink = WebMvcLinkBuilder.linkTo(ActorController.class).slash(id).withSelfRel();
            entityModel.add(selfLink);

            return ResponseEntity.ok(actorOutput);


        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/page/{pageNo}")
    public ResponseEntity<CollectionModel<EntityModel<ActorOutput>>> getActorsByPage(@PathVariable Integer pageNo) {
        Integer pageSize = 10; // Set the desired page size
        List<Actor> actors = service.readAllPaged(pageNo, pageSize, "id");

        if (actors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<EntityModel<ActorOutput>> actorOutputs = actors.stream()
                .map(actor -> {
                    EntityModel<ActorOutput> entityModel = EntityModel.of(ActorOutput.from(actor));
                    Link selfLink = WebMvcLinkBuilder.linkTo(ActorController.class).slash(actor.getId()).withSelfRel();
                    entityModel.add(selfLink);
                    return entityModel;
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ActorOutput>> collectionModel = CollectionModel.of(actorOutputs);

        // Add a next page link if not the last page
        if ((long) pageNo * pageSize < actorRepository.count()) {
            Link nextLink = WebMvcLinkBuilder.linkTo(ActorController.class).slash("page/" + (pageNo + 1)).withRel("next");
            collectionModel.add(nextLink);
        }

        // Add a previous page link if not the first page
        if (pageNo > 0) {
            Link prevLink = WebMvcLinkBuilder.linkTo(ActorController.class).slash("page/" + (pageNo - 1)).withRel("prev");
            collectionModel.add(prevLink);
        }

        return new ResponseEntity<>(collectionModel, new HttpHeaders(), HttpStatus.OK);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActorOutput create(@Validated(ValidationGroup.Create.class) @RequestBody ActorInput data) {
        final var actor = new Actor();
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());
        final var saved = actorRepository.save(actor);
        return ActorOutput.from(saved);
    }

    @PatchMapping("/{id}")
    public ActorOutput partialUpdateById(@PathVariable Short id,
                                         @Validated(ValidationGroup.Update.class) @RequestBody ActorInput data) {
        return actorRepository.findById(id)
                .map(actor -> {
                    if (data.getFirstName() != null) {
                        actor.setFirstName(data.getFirstName());
                    }
                    if (data.getLastName() != null) {
                        actor.setLastName(data.getLastName());
                    }
                    final var saved = actorRepository.save(actor);
                    return ActorOutput.from(saved);
                })
                .orElseThrow(() -> new EntityNotFoundException("Actor not found with id " + id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Short id) {
        actorRepository.deleteById(id);
    }


    @Autowired
    ActorService service;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ActorOutput>>> readAllPaged(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {

        List<Actor> actors = service.readAllPaged(pageNo, pageSize, sortBy);
        List<EntityModel<ActorOutput>> actorOutputs = actors.stream()
                .map(actor -> {
                    EntityModel<ActorOutput> entityModel = EntityModel.of(ActorOutput.from(actor));
                    Link selfLink = WebMvcLinkBuilder.linkTo(ActorController.class).slash(actor.getId()).withSelfRel();
                    entityModel.add(selfLink);
                    return entityModel;
                })
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ActorOutput>> collectionModel = CollectionModel.of(actorOutputs);
        
        // Add a next page link if not the last page
        if ((long) pageNo * pageSize < actorRepository.count()) {
            Link nextLink = WebMvcLinkBuilder.linkTo(ActorController.class).slash("page/" + (pageNo + 1)).withRel("next");
            collectionModel.add(nextLink);
        }

        return new ResponseEntity<>(collectionModel, new HttpHeaders(), HttpStatus.OK);
    }
}


