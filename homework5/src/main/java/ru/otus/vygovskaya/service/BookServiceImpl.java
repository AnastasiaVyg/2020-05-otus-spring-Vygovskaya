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
import ru.otus.vygovskaya.dto.BookDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<BookDto> getAll() {
        return bookDao.getAll().stream().map(book -> createBookDto(book)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookDto save(String name, long authorId, long genreId, int year) {
        Author author = authorDao.getById(authorId).orElseThrow();
        Genre genre = genreDao.getById(genreId).orElseThrow();
        Book book = new Book(name, author, genre, year);
        bookDao.save(book);
        return createBookDto(book);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<BookDto> getById(long id) {
        Optional<Book> optionalBook = bookDao.getById(id);
        return optionalBook.map(book -> {
            BookDto dto = createBookDto(book);
            dto.getComments().addAll(book.getComments().stream().collect(Collectors.toList()));
            return dto;
        });
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    @Transactional
    @Override
    public boolean updateNameById(long id, String name) {
        Optional<Book> optionalBook = bookDao.getById(id);
        if (optionalBook.isPresent()){
            Book book = optionalBook.get();
            book.setName(name);
            bookDao.save(book);
            return true;
        } else {
            return  false;
        }
    }

    @Override
    public List<BookDto> getAllByAuthorId(long id) {
        Author author = authorDao.getById(id).orElseThrow();
        return bookDao.getAllByAuthorId(author).stream().map(book -> createBookDto(book)).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getAllByGenreId(long id) {
        Genre genre = genreDao.getById(id).orElseThrow();
        return bookDao.getAllByGenreId(genre).stream().map(book -> createBookDto(book)).collect(Collectors.toList());
    }

    private BookDto createBookDto(Book book){
        BookDto dto = new BookDto(book.getId(), book.getName(), book.getAuthor(), book.getGenre(), book.getYear());
        return dto;
    }
}
