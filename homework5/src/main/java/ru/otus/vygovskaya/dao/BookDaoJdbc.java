package ru.otus.vygovskaya.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao{

    private final NamedParameterJdbcOperations jdbcOperations;
    private static final String[] ID_COLUMN = {"id"};

    public BookDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query("select b.id, b.name, b.year, a.id author_id, a.name author_name, a.surname author_surname, " +
                "g.id genre_id, g.name genre_name" +
                " from (books b inner join authors a on a.id = b.author_id) inner join genres g on g.id = b.genre_id", new BookMapper());
    }

    @Override
    public int create(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        params.addValue("year", book.getYear());
        int result = jdbcOperations
                .update("insert into books (name, author_id, genre_id, year) values (:name, :author_id, :genre_id, :year)",
                params, keyHolder, ID_COLUMN);
        book.setId(keyHolder.getKey().longValue());
        return result;
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.queryForObject("select b.id, b.name, b.year, a.id author_id, a.name author_name, a.surname author_surname, " +
                "g.id genre_id, g.name genre_name from (books b inner join authors a on a.id = b.author_id) " +
                " inner join genres g on g.id = b.genre_id where b.id = :id", params, new BookMapper());
    }

    @Override
    public int deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.update("delete from books where id = :id", params);
    }

    @Override
    public int update(long id, String name, long authorId, long genreId, int year) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("author_id", authorId);
        params.put("genre_id", genreId);
        params.put("year", year);
        return jdbcOperations.update("update books set name = :name, author_id = :author_id, genre_id = :genre_id, year = :year" +
                "  where id = :id", params);
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int year = rs.getInt("year");
            Author author = new Author(rs.getLong("author_id"), rs.getString("author_name"), rs.getString("author_surname"));
            Genre genre = new Genre(rs.getLong("genre_id"), rs.getString("genre_name"));
            return new Book(id, name, author, genre, year);
        }
    }
}
