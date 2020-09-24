package ru.otus.vygovskaya.service;

import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.domain.MongoGenre;

@Service
public class GenreToMongoService {

    public MongoGenre toMongoGenre(Genre genre){
        return new MongoGenre(genre.getName());
    }
}
