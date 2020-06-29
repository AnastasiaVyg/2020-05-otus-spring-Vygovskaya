package ru.otus.vygovskaya.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Репозиторий на основе Jdbc для работы с жанрами")
@JdbcTest
@Import({GenreDaoJdbc.class})
class GenreDaoJdbcTest {

    private static final String GENRE_1 = "story";
    private static final String GENRE_2 = "poem";
    private static final String GENRE_3 = "detective";
    private static final String GENRE_4 = "novel";


    private static final int EXPECTED_NUMBER_OF_GENRES = 2;

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @DisplayName("должен возвращать все жанры")
    @Test
    void getAll() {
        List<Genre> genres = genreDaoJdbc.getAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .flatExtracting(Genre::getName)
                .contains(GENRE_1)
                .contains(GENRE_2);
    }

    @DisplayName("должен создавать жанр в БД, а потом возвращать его")
    @Test
    void create() {
        Genre genre = new Genre(3, GENRE_3);
        genreDaoJdbc.create(genre);
        Genre actualGenre = genreDaoJdbc.getById(genre.getId());
        assertThat(actualGenre).isEqualToComparingFieldByField(genre);
    }

    @DisplayName("должен возвращать жанр из БД по его идентификатору")
    @Test
    void getById() {
        Genre actualGenre = genreDaoJdbc.getById(1);
        assertThat(actualGenre).isNotNull();
        assertThat(actualGenre.getName()).isEqualTo(GENRE_1);
    }

    @DisplayName("должен удалять жанр из БД по id")
    @Test
    void deleteById() {
        Genre genre = new Genre(GENRE_4);
        List<Genre> genresAfter1 = genreDaoJdbc.getAll();
        genreDaoJdbc.create(genre);
        List<Genre> genresAfter2 = genreDaoJdbc.getAll();
        genreDaoJdbc.deleteById(genre.getId());
        List<Genre> genresAfter = genreDaoJdbc.getAll();
        assertThat(genresAfter).noneMatch(g -> g.getName().equals(GENRE_4));
    }

    @DisplayName("должен обновлять данные о жанре в БД")
    @Test
    void update() {
        String newName = "New Name";
        Genre genre = genreDaoJdbc.getById(2);
        genre.setName(newName);
        genreDaoJdbc.update(genre);
        Genre actualGenre = genreDaoJdbc.getById(2);
        assertThat(actualGenre.getName()).isEqualTo(newName);
    }
}