package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> getAll();
    BookDto save(String name, long authorId, long genreId, int year);
    Optional<BookDto> getById(long id);
    void deleteById(long id);
    boolean updateNameById(long id, String name);
    List<BookDto> getAllByAuthorId(long id);
    List<BookDto> getAllByGenreId(long id);
}
