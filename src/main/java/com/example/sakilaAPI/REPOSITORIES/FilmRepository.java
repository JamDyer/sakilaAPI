package com.example.sakilaAPI.REPOSITORIES;

import com.example.sakilaAPI.Entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Short> {
}
