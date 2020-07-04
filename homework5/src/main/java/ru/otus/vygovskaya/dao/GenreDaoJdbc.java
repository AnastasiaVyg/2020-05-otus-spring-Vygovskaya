package ru.otus.vygovskaya.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.vygovskaya.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao{

    private final NamedParameterJdbcOperations jdbcOperations;
    private static final String[] ID_COLUMN = {"id"};

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Genre> getAll() {
        return jdbcOperations.query("select * from genres", new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public int create(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcOperations.update("insert into genres (name) values (:name)",
                new BeanPropertySqlParameterSource(genre), keyHolder, ID_COLUMN);
        genre.setId(keyHolder.getKey().longValue());
        return result;
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.queryForObject("select * from genres where id = :id", params, new GenreMapper());
    }

    @Override
    public int deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.update("delete from genres where id = :id", params);
    }

    @Override
    public int update(Genre genre) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        return jdbcOperations.update("update genres set name = :name where id = :id", params);
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
