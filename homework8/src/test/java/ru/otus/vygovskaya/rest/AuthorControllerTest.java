package ru.otus.vygovskaya.rest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.User;
import ru.otus.vygovskaya.repository.UserRepository;
import ru.otus.vygovskaya.security.MongoUserDetailsService;
import ru.otus.vygovskaya.security.RestAuthEntryPoint;
import ru.otus.vygovskaya.security.SecurityConfiguration;
import ru.otus.vygovskaya.service.AuthorService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.vygovskaya.rest.Utils.asJsonString;

@WebMvcTest({AuthorController.class, SecurityConfiguration.class, MongoUserDetailsService.class, RestAuthEntryPoint.class})
@ContextConfiguration(classes = TestConfig.class)
class AuthorControllerTest {

    private static final String AUTHOR_NAME_1 = "Alexander";
    private static final String AUTHOR_SURNAME_1 = "Pushkin";
    private static final String AUTHOR_NAME_2 = "Mikhail";
    private static final String AUTHOR_SURNAME_2 = "Lermontov";
    private static final String AUTHOR_NAME_3 = "Agatha";
    private static final String AUTHOR_SURNAME_3 = "Christie";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void getAllAuthors() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("1", AUTHOR_NAME_1, AUTHOR_SURNAME_1));
        when(authorService.getAll()).thenReturn(authors);

        mvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':'1','name':'Alexander','surname':'Pushkin'}]"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void addAuthor() throws Exception {
        Author author = new Author("2", AUTHOR_NAME_2, AUTHOR_SURNAME_2);
        when(authorService.save(AUTHOR_NAME_2, AUTHOR_SURNAME_2)).thenReturn(author);

        mvc.perform(post("/authors")
                .content(asJsonString(author))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value(AUTHOR_NAME_2))
                .andExpect(jsonPath("$.surname").value(AUTHOR_SURNAME_2));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void updateAuthor() throws Exception {
        when(authorService.update("1", AUTHOR_NAME_3, AUTHOR_SURNAME_3)).thenReturn(true);

        mvc.perform(put("/authors/{id}", "1")
                .content(asJsonString(new Author(AUTHOR_NAME_3, AUTHOR_SURNAME_3)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    void deleteAuthor() throws Exception {
        mvc.perform(delete("/authors/{id}", "1"))
                .andExpect(status().isOk());
    }
}