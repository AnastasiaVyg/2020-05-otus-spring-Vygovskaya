package ru.otus.vygovskaya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.vygovskaya.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
