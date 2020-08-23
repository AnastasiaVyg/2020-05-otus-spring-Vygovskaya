package ru.otus.vygovskaya.repository;

import com.mongodb.client.result.DeleteResult;
import reactor.core.publisher.Mono;
import ru.otus.vygovskaya.domain.Book;

public interface BookRepositoryCustom {

    Mono<Book> update(Book book);
    Mono<DeleteResult> removeBooksByGenreId(String id);
    Mono<DeleteResult> removeBooksByAuthorId(String id);
}
