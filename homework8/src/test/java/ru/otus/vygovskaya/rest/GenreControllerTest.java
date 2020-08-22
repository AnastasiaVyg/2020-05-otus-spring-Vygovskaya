package ru.otus.vygovskaya.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.repository.BookRepository;
import ru.otus.vygovskaya.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class GenreControllerTest {

    private static final String GENRE_1 = "story";
    private static final String GENRE_2 = "poem";
    private static final String GENRE_3 = "detective";

    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    @Qualifier("genreRouters")
    private RouterFunction router;

    private WebTestClient client;

    @BeforeEach
    void before(){
        client = WebTestClient.bindToRouterFunction(router)
                .build();
    }

    @Test
    void getAllGenres(){
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("1",GENRE_1));

        when(genreRepository.findAll()).thenReturn(Flux.fromIterable(genres));

        client.get().uri("/genres").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo(GENRE_1);
    }

    @Test
    void addGenre(){
        Genre genre = new Genre(GENRE_2);
        when(genreRepository.save(genre)).thenReturn(Mono.just(genre));

        client.post().uri("/genres").body(BodyInserters.fromPublisher(Mono.just(genre), Genre.class)).exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("name").isEqualTo(GENRE_2);
    }

    @Test
    void updateGenre(){
        Genre genre = new Genre(GENRE_3);
        when(genreRepository.update("1", GENRE_3)).thenReturn(Mono.just(genre));

        client.put().uri("/genres/{id}", "1").body(BodyInserters.fromPublisher(Mono.just(genre), Genre.class)).exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class).isEqualTo(true);
    }

    @Test
    void deleteGenres(){
        when(genreRepository.deleteById("2")).thenReturn(Mono.empty());
        when(bookRepository.removeBooksByGenreId("2")).thenReturn(Mono.empty());

        client.delete().uri("/genres/{id}", "2").exchange()
                .expectStatus().isOk();
    }


}