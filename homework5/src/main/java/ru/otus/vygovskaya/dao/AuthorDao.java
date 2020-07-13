package ru.otus.vygovskaya.dao;

import ru.otus.vygovskaya.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> getAll();
    Author save(Author author);
    Optional<Author> getById(long id);
    void deleteById(long id);
}
