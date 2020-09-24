package ru.otus.vygovskaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.vygovskaya.domain.MongoBook;

public interface BookRepository extends MongoRepository<MongoBook, String> {
}
