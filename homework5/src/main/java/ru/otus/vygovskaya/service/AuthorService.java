package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();
    Author create(String name, String surname);
    Author getById(long id);
    int deleteById(long id);
    int update(long id, String name, String surname);
}
