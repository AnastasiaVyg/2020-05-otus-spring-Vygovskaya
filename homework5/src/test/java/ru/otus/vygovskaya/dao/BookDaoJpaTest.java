package ru.otus.vygovskaya.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Comment;
import ru.otus.vygovskaya.domain.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами")
@DataJpaTest
@Import({BookDaoJpa.class})
class BookDaoJpaTest {

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
    private static final String AUTHOR_NAME_3 = "Boris";
    private static final String AUTHOR_SURNAME_3 = "Akunin";

    private static final String GENRE_1 = "story";
    private static final String GENRE_2 = "poem";
    private static final String GENRE_3 = "documental";

    private static final int EXPECTED_NUMBER_OF_BOOKS = 3;

    private static final long FIRST_BOOK_ID = 1L;

    @Autowired
    private BookDaoJpa bookDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен возвращать все книги с полной информацией о них")
    @Test
    void getAll() {
        List<Book> books = bookDaoJpa.getAll();
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
    void save() {
        Author author = new Author(0,AUTHOR_NAME_3, AUTHOR_SURNAME_3);
        Genre genre = new Genre(0, GENRE_3);
        Book book = new Book(0, BOOK_NAME_4, author, genre, 2000);
        Comment comment = new Comment(0, "The great book", book);
        List<Comment> comments = Collections.singletonList(comment);
        book.setComments(comments);

        bookDaoJpa.save(book);
        assertThat(book.getId()).isGreaterThan(0);
        Book expectedBook = em.find(Book.class, book.getId());

        assertThat(expectedBook).isNotNull().matches(b -> b.getName().equals(BOOK_NAME_4))
                .matches(b -> b.getAuthor() != null && b.getAuthor().getName().equals(AUTHOR_NAME_3) && b.getAuthor().getSurname().equals(AUTHOR_SURNAME_3)
                        && b.getAuthor().getId() > 0)
                .matches(b -> b.getGenre() != null && b.getGenre().getName().equals(GENRE_3) && b.getGenre().getId() > 0)
                .matches(b -> b.getComments() != null && b.getComments().size() > 0);
    }

    @DisplayName("должен возвращать книгу из БД по ее идентификатору")
    @Test
    void getById() {
        Optional<Book> actualBook = bookDaoJpa.getById(FIRST_BOOK_ID);
        Book expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualBook).isPresent().get()
                .isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("должен удалять книгу из БД по id")
    @Test
    void deleteById() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(book).isNotNull();
        em.detach(book);

        bookDaoJpa.deleteById(FIRST_BOOK_ID);
        Book deletedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(deletedBook).isNull();
    }

    @DisplayName("должен обновлять данные о книге в БД")
    @Test
    void update() {
        String newName = "New Name";

        Book book = em.find(Book.class, FIRST_BOOK_ID);
        String oldName = book.getName();
        em.detach(book);

        bookDaoJpa.updateNameById(FIRST_BOOK_ID, newName);
        Book updatedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(updatedBook.getName()).isNotEqualTo(oldName).isEqualTo(newName);
    }

    @DisplayName("должен возвращать все данные о книгах одного автора по идентификатору")
    @Test
    void getAllByAuthorId() {
        Optional<Book> book = bookDaoJpa.getById(FIRST_BOOK_ID);
        assertThat(book).isPresent();
        Author author = book.get().getAuthor();

        List<Book> books = bookDaoJpa.getAllByAuthorId(author);
        assertThat(books).isNotNull().hasSize(2)
                .flatExtracting(Book::getName)
                .contains(BOOK_NAME_1)
                .contains(BOOK_NAME_2);
    }

    @DisplayName("должен возвращать все данные о книгах одного жанра по идентификатору")
    @Test
    void getAllByGenreId() {
        Optional<Book> book = bookDaoJpa.getById(FIRST_BOOK_ID);
        assertThat(book).isPresent();
        Genre genre = book.get().getGenre();

        List<Book> books = bookDaoJpa.getAllByGenreId(genre);
        assertThat(books).isNotNull().hasSize(2)
                .flatExtracting(Book::getName)
                .contains(BOOK_NAME_1)
                .contains(BOOK_NAME_3);
    }
}