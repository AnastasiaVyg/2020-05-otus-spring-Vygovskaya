package ru.otus.vygovskaya.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.repository.AuthorRepository;
import ru.otus.vygovskaya.repository.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AuthorRouter {

    @Bean
    public RouterFunction<ServerResponse> authorRouters(AuthorRepository authorRepository, BookRepository bookRepository){
        return route()
                .GET("/authors", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(authorRepository.findAll(), Author.class))
                .POST("/authors", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(Author.class)
                                .map(authorRepository::save)
                                .flatMap(authorMono -> ok().contentType(APPLICATION_JSON).body(authorMono, Author.class)))
                .PUT("/authors/{id}", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(Author.class)
                                .map(author -> authorRepository.update(request.pathVariable("id"), author.getName(), author.getSurname()))
                                .flatMap(authorMono -> authorMono)
                                .flatMap(authorMono -> ok().contentType(APPLICATION_JSON).body(Mono.just(true), Boolean.class))
                )
                .DELETE("/authors/{id}", accept(APPLICATION_JSON),
                        request -> bookRepository.removeBooksByAuthorId(request.pathVariable("id"))
                                .zipWith(authorRepository.deleteById(request.pathVariable("id")))
                                .flatMap(__ -> ok().contentType(APPLICATION_JSON).bodyValue(true)))
                .build();
    }
}
