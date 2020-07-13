package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.dao.BookDao;
import ru.otus.vygovskaya.dao.CommentDao;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Comment;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentDao commentDao;

    private final BookDao bookDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, BookDao bookDao){
        this.commentDao = commentDao;
        this.bookDao = bookDao;
    }

    @Override
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    @Transactional
    @Override
    public Comment save(String text, long book_id) {
        Book book = bookDao.getById(book_id).orElseThrow();
        Comment comment = new Comment(text, book);
        return commentDao.save(comment);
    }

    @Override
    public Optional<Comment> getById(long id) {
        return commentDao.getById(id);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentDao.deleteById(id);
    }

    @Transactional
    @Override
    public boolean updateTextById(long id, String text) {
        Optional<Comment> optionalComment = getById(id);
        if (optionalComment.isPresent()){
            Comment comment = optionalComment.get();
            comment.setText(text);
            commentDao.save(comment);
            return true;
        } else {
            return false;
        }
    }
}
