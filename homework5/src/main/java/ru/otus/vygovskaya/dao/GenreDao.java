package ru.otus.vygovskaya.dao;

import ru.otus.vygovskaya.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> getAll();
    Genre save(Genre genre);
    Optional<Genre> getById(long id);
    void deleteById(long id);
}
