package ru.otus.vygovskaya.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Comment;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.repository.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data Jpa для работы с книгами")
@DataJpaTest
class BookDaoJpaTest {

    private static final String BOOK_NAME_1 = "Ruslan and Ludmila";
    private static final String BOOK_NAME_2 = "Story about the fisherman and golden fish";
    private static final String BOOK_NAME_3 = "Borodino";

    private static final long FIRST_BOOK_ID = 1L;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен возвращать все данные о книгах одного автора по идентификатору")
    @Test
    void getAllByAuthorId() {
        Optional<Book> book = bookRepository.findById(FIRST_BOOK_ID);
        assertThat(book).isPresent();
        Author author = book.get().getAuthor();

        List<Book> books = bookRepository.findAllByAuthor(author);
        assertThat(books).isNotNull().hasSize(2)
                .flatExtracting(Book::getName)
                .contains(BOOK_NAME_1)
                .contains(BOOK_NAME_2);
    }

    @DisplayName("должен возвращать все данные о книгах одного жанра по идентификатору")
    @Test
    void getAllByGenreId() {
        Optional<Book> book = bookRepository.findById(FIRST_BOOK_ID);
        assertThat(book).isPresent();
        Genre genre = book.get().getGenre();

        List<Book> books = bookRepository.findAllByGenre(genre);
        assertThat(books).isNotNull().hasSize(2)
                .flatExtracting(Book::getName)
                .contains(BOOK_NAME_1)
                .contains(BOOK_NAME_3);
    }
}