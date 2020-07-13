package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> getAll();
    Comment save(String text, long book_id);
    Optional<Comment> getById(long id);
    void deleteById(long id);
    boolean updateTextById(long id, String text);
}
