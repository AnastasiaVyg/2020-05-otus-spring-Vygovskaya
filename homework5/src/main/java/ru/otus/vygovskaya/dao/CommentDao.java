package ru.otus.vygovskaya.dao;

import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    List<Comment> getAll();
    Comment save(Comment comment);
    Optional<Comment> getById(long id);
    int deleteById(long id);
    int update(long id, String text);
}
