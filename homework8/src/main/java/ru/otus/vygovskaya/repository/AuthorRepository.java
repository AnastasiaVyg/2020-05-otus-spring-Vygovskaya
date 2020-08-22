package ru.otus.vygovskaya.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.vygovskaya.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String>, AuthorRepositoryCustom {
}
