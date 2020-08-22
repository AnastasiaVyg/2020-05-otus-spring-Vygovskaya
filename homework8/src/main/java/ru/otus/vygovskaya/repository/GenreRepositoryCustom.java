package ru.otus.vygovskaya.repository;

import reactor.core.publisher.Mono;
import ru.otus.vygovskaya.domain.Genre;

public interface GenreRepositoryCustom {

    Mono<Genre> update(String id, String name);
}
