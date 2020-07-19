package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Comment;
import ru.otus.vygovskaya.repository.BookRepository;
import ru.otus.vygovskaya.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BookRepository bookRepository){
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Transactional
    @Override
    public Comment save(String text, long book_id) {
        Book book = bookRepository.findById(book_id).orElseThrow();
        Comment comment = new Comment(text, book);
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getById(long id) {
        return commentRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean updateTextById(long id, String text) {
        Optional<Comment> optionalComment = getById(id);
        if (optionalComment.isPresent()){
            Comment comment = optionalComment.get();
            comment.setText(text);
            commentRepository.save(comment);
            return true;
        } else {
            return false;
        }
    }
}
