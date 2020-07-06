package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Comment;
import ru.otus.vygovskaya.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> getAll();
    Comment save(String text, long book_id);
    Optional<Comment> getById(long id);
    int deleteById(long id);
    int updateTextById(long id, String text);
}
