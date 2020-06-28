package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();
    Book create(String name, long authorId, long genreId, int year);
    Book getById(long id);
    int deleteById(long id);
    int update(long id, String name, long authorId, long genreId, int year);
}
