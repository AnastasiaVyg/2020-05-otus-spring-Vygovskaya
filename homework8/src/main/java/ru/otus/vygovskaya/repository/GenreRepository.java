package ru.otus.vygovskaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.vygovskaya.domain.Genre;

@RepositoryRestResource(path="genres")
public interface GenreRepository extends MongoRepository<Genre, String> {
}
