package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();
    Book save(String name, long authorId, long genreId, int year);
    Optional<Book> getById(long id);
    int deleteById(long id);
    int updateNameById(long id, String name);
    List<Book> getAllByAuthorId(long id);
    List<Book> getAllByGenreId(long id);
}
