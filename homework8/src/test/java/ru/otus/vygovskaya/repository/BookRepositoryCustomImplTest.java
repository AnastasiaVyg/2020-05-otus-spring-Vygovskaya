package ru.otus.vygovskaya.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.vygovskaya.config", "ru.otus.vygovskaya.repository", "ru.otus.vygovskaya.events"})
@DisplayName("BookRepository")
class BookRepositoryCustomImplTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("должен при удалении жанра удалять все книги с удаленным жанром")
    void removeBooksByGenreId() {
        int size = bookRepository.findAll().size();

        Genre genre = genreRepository.findAll().get(1);
        genreRepository.delete(genre);

        List<Book> actualBooks = bookRepository.findAll();
        assertThat(actualBooks.size()).isEqualTo(size - 1);
    }

    @Test
    @DisplayName("должен при удалении автора удалять все книги с удаленным автором")
    void removeBooksByAuthorId() {
        int size = bookRepository.findAll().size();

        Author author = authorRepository.findAll().get(0);
        authorRepository.delete(author);

        List<Book> actualBooks = bookRepository.findAll();
        assertThat(actualBooks.size()).isEqualTo(size - 2);
    }
}