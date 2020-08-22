package ru.otus.vygovskaya.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.test.StepVerifier;
import reactor.core.publisher.Flux;
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
    @DisplayName("должен удалять все книги с id жанра")
    void removeBooksByGenreId() {
        StepVerifier.create(bookRepository.removeBooksByGenreId("111111111111111111111111"))
                .assertNext(deleteResult -> assertThat(deleteResult.getDeletedCount()).isGreaterThan(0))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("должен удалять все книги с id автора")
    void removeBooksByAuthorId() {
        StepVerifier.create(bookRepository.removeBooksByAuthorId("222222222222222222222222"))
                .assertNext(deleteResult -> assertThat(deleteResult.getDeletedCount()).isGreaterThan(0))
                .expectComplete()
                .verify();
    }
}