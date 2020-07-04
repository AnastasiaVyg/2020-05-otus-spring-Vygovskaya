package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();
    Genre create(String name);
    Genre getById(long id);
    int deleteById(long id);
    int update(long id, String name);
}
