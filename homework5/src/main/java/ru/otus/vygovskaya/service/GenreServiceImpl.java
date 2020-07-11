package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService{

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Transactional
    @Override
    public Genre save(String name) {
        Genre genre = new Genre(name);
        genreRepository.save(genre);
        return genre;
    }

    @Override
    public Optional<Genre> getById(long id) {
        return genreRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean update(long id, String name) {
        Optional<Genre> optionalGenre = getById(id);
        if (optionalGenre.isPresent()){
            Genre genre = optionalGenre.get();
            genre.setName(name);
            genreRepository.save(genre);
            return true;
        } else {
            return false;
        }
    }
}
