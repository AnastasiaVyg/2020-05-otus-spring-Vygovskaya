package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.dto.BookDto;
import ru.otus.vygovskaya.repository.AuthorRepository;
import ru.otus.vygovskaya.repository.BookRepository;
import ru.otus.vygovskaya.repository.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

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
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream().map(book -> createBookDto(book)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BookDto save(String name, long authorId, long genreId, int year) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        Genre genre = genreRepository.findById(genreId).orElseThrow();
        Book book = new Book(name, author, genre, year);
        bookRepository.save(book);
        return createBookDto(book);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<BookDto> getById(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(book -> {
            BookDto dto = createBookDto(book);
            dto.getComments().addAll(book.getComments().stream().collect(Collectors.toList()));
            return dto;
        });
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean updateNameById(long id, String name) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()){
            Book book = optionalBook.get();
            book.setName(name);
            bookRepository.save(book);
            return true;
        } else {
            return  false;
        }
    }

    @Override
    public List<BookDto> getAllByAuthorId(long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return bookRepository.findAllByAuthor(author).stream().map(book -> createBookDto(book)).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getAllByGenreId(long id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        return bookRepository.findAllByGenre(genre).stream().map(book -> createBookDto(book)).collect(Collectors.toList());
    }

    private BookDto createBookDto(Book book){
        BookDto dto = new BookDto(book.getId(), book.getName(), book.getAuthor(), book.getGenre(), book.getYear());
        return dto;
    }
}
