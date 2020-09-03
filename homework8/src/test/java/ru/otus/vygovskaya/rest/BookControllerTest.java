package ru.otus.vygovskaya.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.rest.dto.BookDto;
import ru.otus.vygovskaya.rest.dto.CommentDto;
import ru.otus.vygovskaya.security.MongoUserDetailsService;
import ru.otus.vygovskaya.security.RestAuthEntryPoint;
import ru.otus.vygovskaya.security.SecurityConfiguration;
import ru.otus.vygovskaya.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.otus.vygovskaya.rest.Utils.asJsonString;

@WebMvcTest({BookController.class, SecurityConfiguration.class, MongoUserDetailsService.class, RestAuthEntryPoint.class})
@ContextConfiguration(classes = TestConfig.class)
class BookControllerTest {

    private static final String BOOK_NAME_1 = "Ruslan and Ludmila";
    private static final String BOOK_NAME_2 = "Story about the fisherman and golden fish";
    private static final String BOOK_NAME_3 = "Borodino";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void getAllBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        Genre genre = new Genre("1", "story");
        Author author = new Author("1", "Alexander", "Pushkin");
        books.add(new Book("1", BOOK_NAME_1, author, genre, 1892));

        when(bookService.getAll()).thenReturn(books);

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':'1','name':'Ruslan and Ludmila', 'authorId':'1', 'genreId':'1', 'year':1892}]"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void addBook() throws Exception {
        Genre genre = new Genre("2", "story");
        Author author = new Author("2", "Alexander", "Pushkin");
        Book book = new Book("2", BOOK_NAME_2, author, genre, 2020);
        BookDto bookDto = new BookDto("2", BOOK_NAME_2, "2", "2", 2020);
        when(bookService.save(BOOK_NAME_2, "2", "2", 2020)).thenReturn(book);

        mvc.perform(post("/books")
                .content(asJsonString(bookDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value(BOOK_NAME_2))
                .andExpect(jsonPath("$.authorId").value("2"))
                .andExpect(jsonPath("$.genreId").value("2"))
                .andExpect(jsonPath("$.year").value(2020));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void updateBook() throws Exception {
        BookDto bookDto = new BookDto("1", BOOK_NAME_3, "1", "1", 1892);
        when(bookService.updateById("1", BOOK_NAME_3, "1", "1", 1892)).thenReturn(true);

        mvc.perform(put("/books/{id}", "1")
                .content(asJsonString(bookDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void deleteBook() throws Exception {
        mvc.perform(delete("/books/{id}", "1"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void getComments() throws Exception {
        Genre genre = new Genre("2", "story");
        Author author = new Author("2", "Alexander", "Pushkin");
        Book book = new Book("2", BOOK_NAME_2, author, genre, 2020);
        book.addComment("Great!");
        book.addComment("Test");
        when(bookService.getById("1")).thenReturn(Optional.of(book));

        mvc.perform(get("/books/{id}/comments", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("['Great!','Test']"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void addComment() throws Exception {
        CommentDto comment = new CommentDto();
        comment.setComment("new comment");
        when(bookService.addComment("1", comment.getComment())).thenReturn(true);

        mvc.perform(post("/books/{id}/comments", "1")
                .content(asJsonString(comment))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}