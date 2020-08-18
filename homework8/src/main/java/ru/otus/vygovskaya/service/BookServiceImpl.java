package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.repository.AuthorRepository;
import ru.otus.vygovskaya.repository.BookRepository;
import ru.otus.vygovskaya.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public Book save(String name, String authorId, String genreId, int year) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        Genre genre = genreRepository.findById(genreId).orElseThrow();
        Book book = new Book(name, author, genre, year);
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getById(String id) {
        return bookRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean updateById(String id, String name, String authorId, String genreId, int year) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()){
            Book book = optionalBook.get();
            book.setName(name);
            Author author = authorRepository.findById(authorId).orElseThrow();
            Genre genre = genreRepository.findById(genreId).orElseThrow();
            book.setAuthor(author);
            book.setGenre(genre);
            book.setYear(year);
            bookRepository.save(book);
            return true;
        } else {
            return  false;
        }
    }

    @Override
    public boolean addComment(String id, String comment) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()){
            Book book = optionalBook.get();
            book.addComment(comment);
            bookRepository.save(book);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public List<Book> getAllByAuthorId(String id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return bookRepository.findAllByAuthor(author);
    }

    @Transactional
    @Override
    public List<Book> getAllByGenreId(String id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        return bookRepository.findAllByGenre(genre);
    }

}
