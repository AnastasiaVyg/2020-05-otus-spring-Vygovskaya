package ru.otus.vygovskaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.vygovskaya.domain.MongoGenre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<MongoGenre, String> {
    Optional<MongoGenre> findMongoGenreByName(String name);
}
