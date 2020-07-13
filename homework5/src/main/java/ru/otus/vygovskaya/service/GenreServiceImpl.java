package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.dao.GenreDao;
import ru.otus.vygovskaya.domain.Genre;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    @Override
    public Genre save(String name) {
        Genre genre = new Genre(name);
        genreDao.save(genre);
        return genre;
    }

    @Override
    public Optional<Genre> getById(long id) {
        return genreDao.getById(id);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        genreDao.deleteById(id);
    }

    @Transactional
    @Override
    public boolean update(long id, String name) {
        Optional<Genre> optionalGenre = getById(id);
        if (optionalGenre.isPresent()){
            Genre genre = optionalGenre.get();
            genre.setName(name);
            genreDao.save(genre);
            return true;
        } else {
            return false;
        }
    }
}
