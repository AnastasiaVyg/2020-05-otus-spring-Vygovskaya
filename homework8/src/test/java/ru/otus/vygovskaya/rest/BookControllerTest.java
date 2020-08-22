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
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.repository.AuthorRepository;
import ru.otus.vygovskaya.repository.BookRepository;
import ru.otus.vygovskaya.repository.GenreRepository;
import ru.otus.vygovskaya.rest.dto.BookDto;
import ru.otus.vygovskaya.rest.dto.CommentDto;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class BookControllerTest {

    private static final String BOOK_NAME_1 = "Ruslan and Ludmila";
    private static final String BOOK_NAME_2 = "Story about the fisherman and golden fish";
    private static final String BOOK_NAME_3 = "Borodino";

    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    @Qualifier("bookRouters")
    private RouterFunction router;

    private WebTestClient client;

    @BeforeEach
    void before(){
        client = WebTestClient.bindToRouterFunction(router)
                .build();
    }

    @Test
    void getAllBooks(){
        List<Book> books = new ArrayList<>();
        Genre genre = new Genre("1", "story");
        Author author = new Author("1", "Alexander", "Pushkin");
        books.add(new Book("1", BOOK_NAME_1, author, genre, 1892));

        when(bookRepository.findAll()).thenReturn(Flux.fromIterable(books));

        client.get().uri("/books").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].name").isEqualTo(BOOK_NAME_1)
                .jsonPath("$[0].authorId").isEqualTo("1")
                .jsonPath("$[0].genreId").isEqualTo("1")
                .jsonPath("$[0].year").isEqualTo(1892);
    }

    @Test
    void addBook(){
        Genre genre = new Genre("2", "story");
        Author author = new Author("2", "Alexander", "Pushkin");
        Book book = new Book("2", BOOK_NAME_2, author, genre, 2020);
        BookDto bookDto = new BookDto("2", BOOK_NAME_2, "2", "2", 2020);
        when(genreRepository.findById("2")).thenReturn(Mono.just(genre));
        when(authorRepository.findById("2")).thenReturn(Mono.just(author));
        when(bookRepository.save(book)).thenReturn(Mono.just(book));

        client.post().uri("/books").body(BodyInserters.fromPublisher(Mono.just(bookDto), BookDto.class)).exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("name").isEqualTo(BOOK_NAME_2)
                .jsonPath("authorId").isEqualTo("2")
                .jsonPath("genreId").isEqualTo("2")
                .jsonPath("year").isEqualTo(2020);
    }

    @Test
    void updateBook(){
        Genre genre = new Genre("2", "story");
        Author author = new Author("2", "Alexander", "Pushkin");
        Book book = new Book("1", BOOK_NAME_2, author, genre, 2020);
        BookDto bookDto = new BookDto("1", BOOK_NAME_2, "2", "2", 2020);
        when(genreRepository.findById("2")).thenReturn(Mono.just(genre));
        when(authorRepository.findById("2")).thenReturn(Mono.just(author));
        when(bookRepository.update(book)).thenReturn(Mono.just(book));

        client.put().uri("/books/{id}", "1").body(BodyInserters.fromPublisher(Mono.just(bookDto), BookDto.class)).exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class).isEqualTo(true);
    }

    @Test
    void deleteBook(){
        when(bookRepository.deleteById("2")).thenReturn(Mono.empty());

        client.delete().uri("/books/{id}", "2").exchange()
                .expectStatus().isOk();
    }

    @Test
    void getComments(){
        Genre genre = new Genre("2", "story");
        Author author = new Author("2", "Alexander", "Pushkin");
        Book book = new Book("2", BOOK_NAME_2, author, genre, 2020);
        book.addComment("Great!");
        book.addComment("Test");
        when(bookRepository.findById("2")).thenReturn(Mono.just(book));

        client.get().uri("/books/{id}/comments", "2").exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("['Great!','Test']");
    }

    @Test
    void addComment(){
        Genre genre = new Genre("2", "story");
        Author author = new Author("2", "Alexander", "Pushkin");
        Book book = new Book("2", BOOK_NAME_2, author, genre, 2020);

        CommentDto comment = new CommentDto();
        comment.setComment("new comment");
        when(bookRepository.findById("2")).thenReturn(Mono.just(book));
        when(bookRepository.save(book)).thenReturn(Mono.just(book));

        client.post().uri("/books/{id}/comments", "2").body(BodyInserters.fromPublisher(Mono.just(comment), CommentDto.class)).exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class).isEqualTo(true);
    }
}