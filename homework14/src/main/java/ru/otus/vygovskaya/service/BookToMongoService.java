package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.domain.*;
import ru.otus.vygovskaya.repository.AuthorRepository;
import ru.otus.vygovskaya.repository.GenreRepository;

@Service
public class BookToMongoService {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookToMongoService(AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public MongoBook toMongoBook(Book book){
        Author author = book.getAuthor();
        Genre genre = book.getGenre();
        MongoAuthor mongoAuthor = authorRepository.findMongoAuthorByNameAndSurname(author.getName(), author.getSurname())
                .orElse(null);
        MongoGenre mongoGenre = genreRepository.findMongoGenreByName(genre.getName()).orElse(null);
        MongoBook mongoBook = new MongoBook(book.getName(), mongoAuthor, mongoGenre, book.getYear());
        book.getComments().stream().forEach(comment -> mongoBook.addComment(comment.getText()));
        return mongoBook;
    }
}
