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
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jdbc для работы с авторами")
@JdbcTest
@Import({AuthorDaoJdbc.class})
class AuthorDaoJdbcTest {

    private static final String AUTHOR_NAME_1 = "Alexander";
    private static final String AUTHOR_SURNAME_1 = "Pushkin";
    private static final String AUTHOR_NAME_2 = "Mikhail";
    private static final String AUTHOR_SURNAME_2 = "Lermontov";
    private static final String AUTHOR_NAME_3 = "Agatha";
    private static final String AUTHOR_SURNAME_3 = "Christie";

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @DisplayName("должен возвращать всех авторов")
    @Test
    void getAll() {
        List<Author> authors = authorDaoJdbc.getAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .flatExtracting(Author::getName, Author::getSurname)
                .contains(AUTHOR_NAME_1, AUTHOR_SURNAME_1)
                .contains(AUTHOR_NAME_2, AUTHOR_SURNAME_2);
    }

    @DisplayName("должен создавать автора в БД, а потом возвращать его")
    @Test
    void create() {
        Author author = new Author(3,AUTHOR_NAME_3, AUTHOR_SURNAME_3);
        authorDaoJdbc.create(author);
        Author actualAuthor = authorDaoJdbc.getById(author.getId());
        assertThat(actualAuthor).isEqualToComparingFieldByField(author);
    }

    @DisplayName("должен возвращать автора из БД по его идентификатору")
    @Test
    void getById() {
        Author actualAuthor = authorDaoJdbc.getById(1);
        assertThat(actualAuthor).isNotNull();
        assertThat(actualAuthor).extracting(Author::getName, Author::getSurname)
                .contains(AUTHOR_NAME_1, AUTHOR_SURNAME_1);
    }

    @DisplayName("должен удалять автора из БД по id")
    @Test
    void deleteById() {
        int countBefore = authorDaoJdbc.getAll().size();
        authorDaoJdbc.deleteById(1);
        List<Author> authorsAfter = authorDaoJdbc.getAll();
        int countAfter = authorsAfter.size();
        assertThat(countBefore - countAfter).isEqualTo(1);
        assertThat(authorsAfter).noneMatch(author -> author.getName().equals(AUTHOR_NAME_1));
    }

    @DisplayName("должен обновлять данные о книге в БД")
    @Test
    void update() {
        String newName = "New Name";
        String newSurname = "New Surname";
        Author author = authorDaoJdbc.getById(2);
        author.setName(newName);
        author.setSurname(newSurname);
        authorDaoJdbc.update(author);
        Author actualAuthor = authorDaoJdbc.getById(2);
        assertThat(actualAuthor).extracting(Author::getName, Author::getSurname).contains(newName, newSurname);
    }
}