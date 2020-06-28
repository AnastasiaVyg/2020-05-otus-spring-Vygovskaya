package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.dao.GenreDao;
import ru.otus.vygovskaya.domain.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Genre create(String name) {
        Genre genre = new Genre(name);
        genreDao.create(genre);
        return genre;
    }

    @Override
    public Genre getById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public int deleteById(long id) {
        return genreDao.deleteById(id);
    }

    @Override
    public int update(long id, String name) {
        return genreDao.update(new Genre(id, name));
    }
}
