package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.repository.BookRepository;
import ru.otus.vygovskaya.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Transactional
    @Override
    public Genre save(String name) {
        Genre genre = new Genre(name);
        genreRepository.save(genre);
        return genre;
    }

    @Override
    public Optional<Genre> getById(String id) {
        return genreRepository.findById(id);

    }

    @Transactional
    @Override
    public void deleteById(String id) {
        genreRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean update(String id, String name) {
        Optional<Genre> optionalGenre = getById(id);
        if (optionalGenre.isPresent()){
            Genre genre = optionalGenre.get();

            List<Book> booksByGenre = bookRepository.findAllByGenre(genre);
            booksByGenre.stream().forEach(book -> {
                Genre bookGenre = book.getGenre();
                bookGenre.setName(name);
            });

            genre.setName(name);
            genreRepository.save(genre);
            bookRepository.saveAll(booksByGenre);

            return true;
        } else {
            return false;
        }
    }
}
