package ru.otus.vygovskaya.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.vygovskaya.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao{

    private final NamedParameterJdbcOperations jdbcOperations;
    private static final String[] ID_COLUMN = {"id"};

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query("select * from authors", new AuthorMapper());
    }

    @Override
    public int create(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcOperations.update("insert into authors (name, surname) values (:name, :surname)",
                new BeanPropertySqlParameterSource(author), keyHolder, ID_COLUMN);
        author.setId(keyHolder.getKey().longValue());
        return result;
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.queryForObject("select * from authors where id = :id", params, new AuthorMapper());
    }

    @Override
    public int deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbcOperations.update("delete from authors where id = :id", params);
    }

    @Override
    public int update(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", author.getName());
        params.put("surname", author.getSurname());
        params.put("id", author.getId());
        return jdbcOperations.update("update authors set name = :name, surname = :surname where id = :id", params);
    }

    private static class AuthorMapper implements RowMapper<Author>{

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            return new Author(id, name, surname);
        }
    }
}
