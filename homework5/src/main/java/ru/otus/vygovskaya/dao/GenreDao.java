package ru.otus.vygovskaya.dao;

import ru.otus.vygovskaya.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();
    int create(Genre genre);
    Genre getById(long id);
    int deleteById(long id);
    int update(Genre genre);
}
