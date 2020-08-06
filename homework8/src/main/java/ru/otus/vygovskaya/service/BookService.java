package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();
    Book save(String name, String authorId, String genreId, int year);
    Optional<Book> getById(String id);
    void deleteById(String id);
    boolean updateNameById(String id, String name);
    List<Book> getAllByAuthorId(String id);
    List<Book> getAllByGenreId(String id);
}
