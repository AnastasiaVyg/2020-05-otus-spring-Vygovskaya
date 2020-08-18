package ru.otus.vygovskaya.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.service.GenreService;

import java.util.List;

@RestController
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres(){
        return genreService.getAll();
    }

    @PostMapping("/genres")
    public Genre addGenre(@RequestBody Genre genre){
        return genreService.save(genre.getName());
    }

    @PutMapping("/genres/{id}")
    public boolean updateGenre(@PathVariable String id, @RequestBody Genre genre){
        return genreService.update(id, genre.getName());
    }

    @DeleteMapping("/genres/{id}")
    public void deleteGenres(@PathVariable String id){
        genreService.deleteById(id);
    }
}
