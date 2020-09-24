package ru.otus.vygovskaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.vygovskaya.domain.MongoAuthor;

import java.util.Optional;


public interface AuthorRepository extends MongoRepository<MongoAuthor, String> {

    Optional<MongoAuthor> findMongoAuthorByNameAndSurname(String name, String surname);
}
