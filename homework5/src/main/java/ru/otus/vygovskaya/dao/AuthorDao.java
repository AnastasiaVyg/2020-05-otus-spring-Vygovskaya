package ru.otus.vygovskaya.dao;

import org.springframework.dao.DataAccessException;
import ru.otus.vygovskaya.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> getAll();
    Author save(Author author);
    Optional<Author> getById(long id);
    int deleteById(long id);
    int update(long id, String name, String surname);
}
