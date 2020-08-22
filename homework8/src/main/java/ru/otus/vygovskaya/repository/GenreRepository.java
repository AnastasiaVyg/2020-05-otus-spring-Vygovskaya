package ru.otus.vygovskaya.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.vygovskaya.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String>, GenreRepositoryCustom {

}
