package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Transactional
    @Override
    public Author save(String name, String surname) {
        Author author = new Author(name, surname);
        authorRepository.save(author);
        return author;
    }

//    @Transactional(readOnly = true)
    @Override
    public Optional<Author> getById(long id) {
        return authorRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean update(long id, String name, String surname) {
        Optional<Author> optionalAuthor = getById(id);
        if (optionalAuthor.isPresent()){
            Author author = optionalAuthor.get();
            author.setName(name);
            author.setSurname(surname);
            authorRepository.save(author);
            return true;
        } else {
            return false;
        }
    }
}
