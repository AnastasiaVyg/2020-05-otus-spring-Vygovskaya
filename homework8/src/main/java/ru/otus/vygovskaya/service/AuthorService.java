package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> getAll();
    Author save(String name, String surname);
    Optional<Author> getById(String id);
    void deleteById(String id);
    boolean update(String id, String name, String surname);
}
