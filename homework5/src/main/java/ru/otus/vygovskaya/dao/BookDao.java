package ru.otus.vygovskaya.dao;

import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    List<Book> getAll();
    Book save(Book book);
    Optional<Book> getById(long id);
    int deleteById(long id);
    int updateNameById(long id, String name);
    List<Book> getAllByAuthorId(Author author);
    List<Book> getAllByGenreId(Genre genre);
}
