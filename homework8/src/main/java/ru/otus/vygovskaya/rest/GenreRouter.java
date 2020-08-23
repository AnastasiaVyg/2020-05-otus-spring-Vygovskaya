package ru.otus.vygovskaya.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.repository.BookRepository;
import ru.otus.vygovskaya.repository.GenreRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class GenreRouter {

    @Bean
    public RouterFunction<ServerResponse> genreRouters(GenreRepository genreRepository, BookRepository bookRepository){
        return route()
                .GET("/genres", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(genreRepository.findAll(), Genre.class))
                .POST("/genres", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(Genre.class)
                                .map(genreRepository::save)
                                .flatMap(genreMono -> ok().contentType(APPLICATION_JSON).body(genreMono, Genre.class)))
                .PUT("/genres/{id}", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(Genre.class)
                                .map(genre -> genreRepository.update(request.pathVariable("id"), genre.getName()))
                                .flatMap(genreMono -> genreMono)
                                .flatMap(genreMono -> ok().contentType(APPLICATION_JSON).body(Mono.just(true), Boolean.class))
                )
                .DELETE("/genres/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.removeBooksByGenreId(request.pathVariable("id"))
                                .zipWith(genreRepository.deleteById(request.pathVariable("id")))
                                 .flatMap(__ -> ok().contentType(APPLICATION_JSON).bodyValue(true))
                                )
        .build();
    }
}
