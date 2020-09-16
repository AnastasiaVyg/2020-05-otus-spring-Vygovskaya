package ru.otus.vygovskaya.service;

import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.MongoAuthor;

@Service
public class AuthorToMongoService {

    public MongoAuthor toMongoAuthor(Author author){
        return new MongoAuthor(author.getName(), author.getSurname());
    }
}
