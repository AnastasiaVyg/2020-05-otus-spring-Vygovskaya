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
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.repository.AuthorRepository;
import ru.otus.vygovskaya.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class AuthorControllerTest {

    private static final String AUTHOR_NAME_1 = "Alexander";
    private static final String AUTHOR_SURNAME_1 = "Pushkin";
    private static final String AUTHOR_NAME_2 = "Mikhail";
    private static final String AUTHOR_SURNAME_2 = "Lermontov";
    private static final String AUTHOR_NAME_3 = "Agatha";
    private static final String AUTHOR_SURNAME_3 = "Christie";

    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    @Qualifier("authorRouters")
    private RouterFunction  router;

    private WebTestClient client;

    @BeforeEach
    void before(){
        client = WebTestClient.bindToRouterFunction(router)
                .build();
    }

    @Test
    void getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        Author author1 = new Author(AUTHOR_NAME_1, AUTHOR_SURNAME_1);
        Author author2 = new Author(AUTHOR_NAME_2, AUTHOR_SURNAME_2);
        authors.add(author1);
        authors.add(author2);

        when(authorRepository.findAll()).thenReturn(Flux.fromIterable(authors));

        client.get().uri("/authors").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo(AUTHOR_NAME_1)
                .jsonPath("$[0].surname").isEqualTo(AUTHOR_SURNAME_1)
                .jsonPath("$[1].name").isEqualTo(AUTHOR_NAME_2)
                .jsonPath("$[1].surname").isEqualTo(AUTHOR_SURNAME_2);
    }

    @Test
    void addAuthor(){
        Author author = new Author(AUTHOR_NAME_3, AUTHOR_SURNAME_3);
        when(authorRepository.save(author)).thenReturn(Mono.just(author));

        client.post().uri("/authors").body(BodyInserters.fromPublisher(Mono.just(author), Author.class)).exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("name").isEqualTo(AUTHOR_NAME_3)
                .jsonPath("surname").isEqualTo(AUTHOR_SURNAME_3);
    }

    @Test
    void updateAuthor(){
        String newName = "newName";
        String newSurname = "newSurname";
        Author edit = new Author("1", newName, newSurname);
        when(authorRepository.update("1", newName, newSurname)).thenReturn(Mono.just(edit));

        client.put().uri("/authors/{id}", "1").body(BodyInserters.fromPublisher(Mono.just(edit), Author.class)).exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class).isEqualTo(true);

    }

    @Test
    void deleteAuthor(){
        when(authorRepository.deleteById("2")).thenReturn(Mono.empty());
        when(bookRepository.removeBooksByAuthorId("2")).thenReturn(Mono.empty());

        client.delete().uri("/authors/{id}", "2").exchange()
                .expectStatus().isOk();
    }
}