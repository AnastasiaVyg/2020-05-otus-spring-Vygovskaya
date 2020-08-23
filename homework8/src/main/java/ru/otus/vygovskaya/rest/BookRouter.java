package ru.otus.vygovskaya.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.repository.AuthorRepository;
import ru.otus.vygovskaya.repository.BookRepository;
import ru.otus.vygovskaya.repository.GenreRepository;
import ru.otus.vygovskaya.rest.dto.BookDto;
import ru.otus.vygovskaya.rest.dto.CommentDto;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class BookRouter {

    @Bean
    public RouterFunction<ServerResponse> bookRouters(AuthorRepository authorRepository, BookRepository bookRepository, GenreRepository genreRepository){
        return route()
                .GET("/books", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(bookRepository.findAll()
                                .map(book -> book.getDto()), BookDto.class))
                .POST("/books", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(BookDto.class)
                                .map(bookDto -> {
                                    Mono<Author> authorMono = authorRepository.findById(bookDto.getAuthorId());
                                    Mono<Genre> genreMono = genreRepository.findById(bookDto.getGenreId());
                                    return authorMono.zipWith(genreMono, (author, genre) -> new Book(bookDto.getName(), author, genre, bookDto.getYear()))
                                            .map(bookRepository::save);
                                })
                                .flatMap(bookMono -> bookMono)
                                .flatMap(bookMono -> bookMono)
                                .flatMap(bookMono -> ok().contentType(APPLICATION_JSON).body(Mono.just(bookMono.getDto()), BookDto.class)))
                .PUT("/books/{id}", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(BookDto.class)
                                .map(bookDto -> {
                                    Mono<Author> authorMono = authorRepository.findById(bookDto.getAuthorId());
                                    Mono<Genre> genreMono = genreRepository.findById(bookDto.getGenreId());
                                    return authorMono.zipWith(genreMono, (author, genre) -> new Book(request.pathVariable("id"), bookDto.getName(), author, genre, bookDto.getYear()))
                                            .map(bookRepository::update);
                                })
                                .flatMap(bookMono -> bookMono)
                                .flatMap(bookMono -> bookMono)
                                .flatMap(bookMono -> ok().contentType(APPLICATION_JSON).body(Mono.just(true), Boolean.class))
                )
                .DELETE("/books/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.deleteById(request.pathVariable("id"))
                                .flatMap(__ -> ok().contentType(APPLICATION_JSON).bodyValue(true)))
                .GET("/books/{id}/comments", accept(APPLICATION_JSON),
                        request -> bookRepository.findById(request.pathVariable("id"))
                                .flatMap(bookMono -> ok().contentType(APPLICATION_JSON).body(Flux.fromIterable(bookMono.getComments()), List.class)))
                .POST("/books/{id}/comments", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(CommentDto.class)
                                .zipWith(bookRepository.findById(request.pathVariable("id")), (commentDto, book) -> {
                                    book.addComment(commentDto.getComment());
                                    return book;
                                }).map(bookRepository::save)
                                .flatMap(bookMono -> bookMono)
                                .flatMap(bookMono -> ok().contentType(APPLICATION_JSON).body(Mono.just(true), Boolean.class))
                        )
                .build();
    }
}
