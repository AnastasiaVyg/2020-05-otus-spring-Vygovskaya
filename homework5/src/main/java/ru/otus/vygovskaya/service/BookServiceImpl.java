package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.dao.AuthorDao;
import ru.otus.vygovskaya.dao.BookDao;
import ru.otus.vygovskaya.dao.GenreDao;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Transactional
    @Override
    public Book save(String name, long authorId, long genreId, int year) {
        Author author = authorDao.getById(authorId).orElseThrow();
        Genre genre = genreDao.getById(genreId).orElseThrow();
        Book book = new Book(name, author, genre, year);
        bookDao.save(book);
        return book;
    }

    @Override
    public Optional<Book> getById(long id) {
        return bookDao.getById(id);
    }

    @Transactional
    @Override
    public int deleteById(long id) {
        return bookDao.deleteById(id);
    }

    @Transactional
    @Override
    public int updateNameById(long id, String name) {
        return bookDao.updateNameById(id, name);
    }

    @Override
    public List<Book> getAllByAuthorId(long id) {
        Author author = authorDao.getById(id).orElseThrow();
        return bookDao.getAllByAuthorId(author);
    }

    @Override
    public List<Book> getAllByGenreId(long id) {
        Genre genre = genreDao.getById(id).orElseThrow();
        return bookDao.getAllByGenreId(genre);
    }
}
