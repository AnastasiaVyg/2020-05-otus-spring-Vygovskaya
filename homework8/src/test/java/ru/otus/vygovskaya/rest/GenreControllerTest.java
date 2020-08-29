package ru.otus.vygovskaya.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.security.MongoUserDetailsService;
import ru.otus.vygovskaya.security.RestAuthEntryPoint;
import ru.otus.vygovskaya.security.SecurityConfiguration;
import ru.otus.vygovskaya.service.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.vygovskaya.rest.Utils.asJsonString;

@WebMvcTest({GenreController.class, SecurityConfiguration.class, MongoUserDetailsService.class, RestAuthEntryPoint.class})
@ContextConfiguration(classes = TestConfig.class)
class GenreControllerTest {

    private static final String GENRE_1 = "story";
    private static final String GENRE_2 = "poem";
    private static final String GENRE_3 = "detective";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void getAllGenres() throws Exception {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("1",GENRE_1));

        when(genreService.getAll()).thenReturn(genres);

        mvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':'1','name':'story'}]"));
    }

    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void addGenre() throws Exception {
        when(genreService.save(GENRE_2)).thenReturn(new Genre("2", GENRE_2));

        mvc.perform(post("/genres")
                .content(asJsonString(new Genre(GENRE_2)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value(GENRE_2));
    }

    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void updateGenre() throws Exception {
        when(genreService.update("1", GENRE_3)).thenReturn(true);

        mvc.perform(put("/genres/{id}", "1")
                .content(asJsonString(new Genre(GENRE_3)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @Test
    void deleteGenres() throws Exception {
        mvc.perform(delete("/genres/{id}", "1"))
                .andExpect(status().isOk());
    }


}