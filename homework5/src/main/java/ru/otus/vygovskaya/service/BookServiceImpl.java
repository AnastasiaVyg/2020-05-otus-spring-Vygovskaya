package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.dao.AuthorDao;
import ru.otus.vygovskaya.dao.BookDao;
import ru.otus.vygovskaya.dao.GenreDao;
import ru.otus.vygovskaya.domain.Book;

import java.util.List;

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

    @Override
    public Book create(String name, long authorId, long genreId, int year) {
        Book book = new Book(name, authorDao.getById(authorId), genreDao.getById(genreId), year);
        bookDao.create(book);
        return book;
    }

    @Override
    public Book getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public int deleteById(long id) {
        return bookDao.deleteById(id);
    }

    @Override
    public int update(long id, String name, long authorId, long genreId, int year) {
        return bookDao.update(id, name, authorId, genreId, year);
    }

    @Override
    public List<Book> getAllByAuthorId(long id) {
        return bookDao.getAllByAuthorId(id);
    }

    @Override
    public List<Book> getAllByGenreId(long id) {
        return bookDao.getAllByGenreId(id);
    }
}
