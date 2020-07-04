package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.dao.AuthorDao;
import ru.otus.vygovskaya.domain.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao){
        this.authorDao = authorDao;
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public Author create(String name, String surname) {
        Author author = new Author(name, surname);
        authorDao.create(author);
        return author;
    }

    @Override
    public Author getById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public int deleteById(long id) {
        return authorDao.deleteById(id);
    }

    @Override
    public int update(long id, String name, String surname) {
        return authorDao.update(new Author(id, name, surname));
    }
}
