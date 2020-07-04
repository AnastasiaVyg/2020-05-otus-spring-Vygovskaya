package ru.otus.vygovskaya.dao;

import org.springframework.dao.DataAccessException;
import ru.otus.vygovskaya.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAll();
    int create(Author author);
    Author getById(long id);
    int deleteById(long id);
    int update(Author author);
}
