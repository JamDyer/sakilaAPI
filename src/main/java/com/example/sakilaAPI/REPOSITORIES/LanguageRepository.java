package com.example.sakilaAPI.REPOSITORIES;

import com.example.sakilaAPI.Entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Short> {
}
