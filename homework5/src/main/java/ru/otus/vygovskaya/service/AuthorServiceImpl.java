package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.dao.AuthorDao;
import ru.otus.vygovskaya.domain.Author;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    @Override
    public Author save(String name, String surname) {
        Author author = new Author(name, surname);
        authorDao.save(author);
        return author;
    }

    @Override
    public Optional<Author> getById(long id) {
        return authorDao.getById(id);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        authorDao.deleteById(id);
    }

    @Transactional
    @Override
    public boolean update(long id, String name, String surname) {
        Optional<Author> optionalAuthor = getById(id);
        if (optionalAuthor.isPresent()){
            Author author = optionalAuthor.get();
            author.setName(name);
            author.setSurname(surname);
            authorDao.save(author);
            return true;
        } else {
            return false;
        }
    }
}
