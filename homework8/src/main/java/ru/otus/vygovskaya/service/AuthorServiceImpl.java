package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.repository.AuthorRepository;
import ru.otus.vygovskaya.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository){
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Transactional
    @Override
    public Author save(String name, String surname) {
        Author author = new Author(name, surname);
        authorRepository.save(author);
        return author;
    }

    @Override
    public Optional<Author> getById(String id) {
        return authorRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean update(String id, String name, String surname) {
        Optional<Author> optionalAuthor = getById(id);
        if (optionalAuthor.isPresent()){
            Author author = optionalAuthor.get();

            List<Book> booksByAuthor = bookRepository.findAllByAuthor(author);
            booksByAuthor.stream().forEach(book -> {
                Author authorBook = book.getAuthor();
                authorBook.setName(name);
                authorBook.setSurname(surname);
            });

            author.setName(name);
            author.setSurname(surname);
            authorRepository.save(author);
            bookRepository.saveAll(booksByAuthor);

            return true;
        } else {
            return false;
        }
    }
}
