package ru.otus.vygovskaya.dao;

import liquibase.pro.packaged.G;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Genre;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами")
@DataJpaTest
@Import({GenreDaoJpa.class})
class GenreDaoJpaTest {

    private static final String GENRE_1 = "story";
    private static final String GENRE_2 = "poem";
    private static final String GENRE_3 = "detective";
    private static final String GENRE_4 = "novel";

    private static final long FIRST_GENRE_ID = 1L;

    private static final int EXPECTED_NUMBER_OF_GENRES = 2;

    @Autowired
    private GenreDaoJpa genreDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен возвращать все жанры")
    @Test
    void getAll() {
        List<Genre> genres = genreDaoJpa.getAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .flatExtracting(Genre::getName)
                .contains(GENRE_1)
                .contains(GENRE_2);
    }

    @DisplayName("должен создавать жанр в БД, а потом возвращать его")
    @Test
    void save() {
        Genre genre = new Genre(0, GENRE_3);
        genreDaoJpa.save(genre);
        assertThat(genre.getId()).isGreaterThan(0);
        Genre expectedGenre = em.find(Genre.class, genre.getId());
        assertThat(genre).isEqualToComparingFieldByField(expectedGenre);
    }

    @DisplayName("должен возвращать жанр из БД по его идентификатору")
    @Test
    void getById() {
        Optional<Genre> actualGenre = genreDaoJpa.getById(FIRST_GENRE_ID);
        Genre expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(actualGenre).isPresent().get()
                .isEqualToComparingFieldByField(expectedGenre);
    }

    @DisplayName("должен удалять жанр из БД по id")
    @Test
    void deleteById() {
        Genre genre = genreDaoJpa.save(new Genre(GENRE_4));
        long deletedGenreId = genre.getId();

        Genre createdGenre = em.find(Genre.class, deletedGenreId);
        assertThat(createdGenre).isNotNull();
        em.detach(createdGenre);

        genreDaoJpa.deleteById(deletedGenreId);
        Genre deletedGenre = em.find(Genre.class, deletedGenreId);
        assertThat(deletedGenre).isNull();
    }

    @DisplayName("должен обновлять данные о жанре в БД")
    @Test
    void update() {
        String newName = "New Name";

        Genre genre = em.find(Genre.class, FIRST_GENRE_ID);
        String oldName = genre.getName();
        em.detach(genre);

        Optional<Genre> genreDaoJpaById = genreDaoJpa.getById(FIRST_GENRE_ID);
        assertThat(genreDaoJpaById).isPresent();
        genreDaoJpaById.get().setName(newName);
        genreDaoJpa.save(genreDaoJpaById.get());
        Genre updatedGenre = em.find(Genre.class, FIRST_GENRE_ID);

        assertThat(updatedGenre.getName()).isNotEqualTo(oldName).isEqualTo(newName);
    }
}