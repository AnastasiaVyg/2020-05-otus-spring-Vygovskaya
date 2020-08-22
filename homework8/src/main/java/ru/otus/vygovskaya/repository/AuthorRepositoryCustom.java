package ru.otus.vygovskaya.repository;

import reactor.core.publisher.Mono;
import ru.otus.vygovskaya.domain.Author;

public interface AuthorRepositoryCustom {

    Mono<Author> update(String id, String name, String surname);
}
