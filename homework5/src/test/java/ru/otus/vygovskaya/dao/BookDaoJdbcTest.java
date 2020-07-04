package ru.otus.vygovskaya.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jdbc для работы с книгами")
@JdbcTest
@Import({BookDaoJdbc.class})
class BookDaoJdbcTest {

    private static final String BOOK_NAME_1 = "Ruslan and Ludmila";
    private static final String BOOK_NAME_2 = "Story about the fisherman and golden fish";
    private static final String BOOK_NAME_3 = "Borodino";
    private static final String BOOK_NAME_4 = "The Golden Cockerel";

    private static final int YEAR_1 = 1820;
    private static final int YEAR_2 = 1835;
    private static final int YEAR_3 = 1837;
    private static final int YEAR_4 = 1834;

    private static final String AUTHOR_NAME_1 = "Alexander";
    private static final String AUTHOR_SURNAME_1 = "Pushkin";
    private static final String AUTHOR_NAME_2 = "Mikhail";
    private static final String AUTHOR_SURNAME_2 = "Lermontov";

    private static final String GENRE_1 = "story";
    private static final String GENRE_2 = "poem";

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @DisplayName("должен возвращать все книги с полной информацией о них")
    @Test
    void getAll() {
        List<Book> books = bookDaoJdbc.getAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(book -> book.getGenre() != null)
                .allMatch(book -> book.getAuthor() != null)
                .anyMatch(book -> book.getAuthor().getName().equals(AUTHOR_NAME_1) && book.getAuthor().getSurname().equals(AUTHOR_SURNAME_1))
                .anyMatch(book -> book.getAuthor().getName().equals(AUTHOR_NAME_2) && book.getAuthor().getSurname().equals(AUTHOR_SURNAME_2))
                .anyMatch(book -> book.getGenre().getName().equals(GENRE_1))
                .anyMatch(book -> book.getGenre().getName().equals(GENRE_2))
                .flatExtracting(Book::getName, Book::getYear)
                .contains(BOOK_NAME_1, YEAR_1)
                .contains(BOOK_NAME_2, YEAR_2)
                .contains(BOOK_NAME_3, YEAR_3);
    }

    @DisplayName("должен создавать книгу в БД, а потом возвращать ее")
    @Test
    void create() {
        Book book = new Book(BOOK_NAME_4, new Author(1,AUTHOR_NAME_1, AUTHOR_SURNAME_1), new Genre(1,GENRE_1), YEAR_4);
        bookDaoJdbc.create(book);
        Book actualBook = bookDaoJdbc.getById(book.getId());
        assertThat(actualBook).isEqualToComparingFieldByField(book);
    }

    @DisplayName("должен возвращать книгу из БД по ее идентификатору")
    @Test
    void getById() {
        Book actualBook = bookDaoJdbc.getById(1);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook).extracting(Book::getName, Book::getYear)
                .contains(BOOK_NAME_1, YEAR_1);
        assertThat(actualBook.getAuthor()).extracting(Author::getId, Author::getName, Author::getSurname)
                .contains(1L, AUTHOR_NAME_1, AUTHOR_SURNAME_1);
    }

    @DisplayName("должен удалять книгу из БД по id")
    @Test
    void deleteById() {
        int countBefore = bookDaoJdbc.getAll().size();
        bookDaoJdbc.deleteById(1);
        List<Book> booksAfter = bookDaoJdbc.getAll();
        int countAfter = booksAfter.size();
        assertThat(countBefore - countAfter).isEqualTo(1);
        assertThat(booksAfter).noneMatch(book -> book.getName().equals(BOOK_NAME_1));
    }

    @DisplayName("должен обновлять данные о книге в БД")
    @Test
    void update() {
        String newName = "New Name";
        int newYear = 1900;
        Book book = bookDaoJdbc.getById(2);
        bookDaoJdbc.update(book.getId(), newName, book.getAuthor().getId(), book.getGenre().getId(), newYear);
        Book actualBook = bookDaoJdbc.getById(2);
        assertThat(actualBook).extracting(Book::getName, Book::getYear).contains(newName, newYear);
    }

    @DisplayName("должен возвращать все данные о книгах одного автора по идентификатору")
    @Test
    void getAllByAuthorId() {
        List<Book> books = bookDaoJdbc.getAllByAuthorId(1);
        assertThat(books).isNotNull().hasSize(2)
                .flatExtracting(Book::getName)
                .contains(BOOK_NAME_1)
                .contains(BOOK_NAME_2);
    }

    @DisplayName("должен возвращать все данные о книгах одного жанра по идентификатору")
    @Test
    void getAllByGenreId() {
        List<Book> books = bookDaoJdbc.getAllByGenreId(2);
        assertThat(books).isNotNull().hasSize(2)
                .flatExtracting(Book::getName)
                .contains(BOOK_NAME_1)
                .contains(BOOK_NAME_3);
    }
}