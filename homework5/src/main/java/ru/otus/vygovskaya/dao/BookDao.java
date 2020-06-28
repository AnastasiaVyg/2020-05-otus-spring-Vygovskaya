package ru.otus.vygovskaya.dao;

import ru.otus.vygovskaya.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();
    int create(Book book);
    Book getById(long id);
    int deleteById(long id);
    int update(long id, String name, long authorId, long genreId, int year);
}
