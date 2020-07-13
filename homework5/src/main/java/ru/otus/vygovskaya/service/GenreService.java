package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> getAll();
    Genre save(String name);
    Optional<Genre> getById(long id);
    void deleteById(long id);
    boolean update(long id, String name);
}
