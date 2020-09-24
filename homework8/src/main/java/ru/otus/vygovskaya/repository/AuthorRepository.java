package ru.otus.vygovskaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.vygovskaya.domain.Author;

@RepositoryRestResource(path="authors")
public interface AuthorRepository extends MongoRepository<Author, String> {
}
