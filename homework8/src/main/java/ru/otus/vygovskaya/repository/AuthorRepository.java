package ru.otus.vygovskaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.vygovskaya.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
