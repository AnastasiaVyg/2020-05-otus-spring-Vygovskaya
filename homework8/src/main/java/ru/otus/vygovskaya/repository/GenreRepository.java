package ru.otus.vygovskaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.vygovskaya.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
