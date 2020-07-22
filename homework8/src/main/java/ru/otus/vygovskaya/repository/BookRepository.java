package ru.otus.vygovskaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    List<Book> findAllByAuthor(Author author);
    List<Book> findAllByGenre(Genre genre);
}
