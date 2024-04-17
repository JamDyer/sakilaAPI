package com.example.sakilaAPI.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "film")
@Getter
@Setter
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "film_id")
    private Short id;

    @Column( name = "title")
    private String title;

    @Column( name = "rental_duration")
    private Short duration;

    @Column( name = "rental_rate")
    private BigDecimal rate;

    @Column( name = "replacement_cost")
    private BigDecimal cost;

    @Column( name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Language language;

    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    private List<Actor> cast = new ArrayList<>();

}