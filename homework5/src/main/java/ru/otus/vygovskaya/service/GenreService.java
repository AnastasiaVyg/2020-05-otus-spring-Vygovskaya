package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> getAll();
    Genre save(String name);
    Optional<Genre> getById(long id);
    int deleteById(long id);
    int update(long id, String name);
}
