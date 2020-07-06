package ru.otus.vygovskaya.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.vygovskaya.domain.Author;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами")
@DataJpaTest
@Import({AuthorDaoJpa.class})
class AuthorDaoJpaTest {

    private static final String AUTHOR_NAME_1 = "Alexander";
    private static final String AUTHOR_SURNAME_1 = "Pushkin";
    private static final String AUTHOR_NAME_2 = "Mikhail";
    private static final String AUTHOR_SURNAME_2 = "Lermontov";
    private static final String AUTHOR_NAME_3 = "Agatha";
    private static final String AUTHOR_SURNAME_3 = "Christie";

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;

    private static final long FIRST_AUTHOR_ID = 1L;

    @Autowired
    private AuthorDaoJpa authorDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен возвращать всех авторов")
    @Test
    void getAll() {
        List<Author> authors = authorDaoJpa.getAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .flatExtracting(Author::getName, Author::getSurname)
                .contains(AUTHOR_NAME_1, AUTHOR_SURNAME_1)
                .contains(AUTHOR_NAME_2, AUTHOR_SURNAME_2);
    }

    @DisplayName("должен создавать автора в БД, а потом возвращать его")
    @Test
    void save() {
        Author author = new Author(0, AUTHOR_NAME_3, AUTHOR_SURNAME_3);
        authorDaoJpa.save(author);
        assertThat(author.getId()).isGreaterThan(0);
        Author expectedAuthor = em.find(Author.class, author.getId());
        assertThat(author).isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("должен возвращать автора из БД по его идентификатору")
    @Test
    void getById() {
        Optional<Author> actualAuthor = authorDaoJpa.getById(FIRST_AUTHOR_ID);
        Author expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(actualAuthor).isPresent().get()
                .isEqualToComparingFieldByField(expectedAuthor);
    }

    @DisplayName("должен удалять автора из БД по id")
    @Test
    void deleteById() {
        Author author = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(author).isNotNull();
        em.detach(author);

        authorDaoJpa.deleteById(FIRST_AUTHOR_ID);
        Author deletedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(deletedAuthor).isNull();
    }

    @DisplayName("должен обновлять данные о книге в БД")
    @Test
    void update() {
        String newName = "New Name";
        String newSurname = "New Surname";

        Author author = em.find(Author.class, FIRST_AUTHOR_ID);
        String oldName = author.getName();
        String oldSurname = author.getSurname();
        em.detach(author);

        authorDaoJpa.update(FIRST_AUTHOR_ID, newName, newSurname);
        Author updatedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(updatedAuthor.getName()).isNotEqualTo(oldName).isEqualTo(newName);
        assertThat(updatedAuthor.getSurname()).isNotEqualTo(oldSurname).isEqualTo(newSurname);
    }
}