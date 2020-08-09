package ru.otus.vygovskaya.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.service.AuthorService;

import java.util.List;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public List<Author> getAllAuthors(){
        return authorService.getAll();
    }

    @PostMapping("/authors")
    public Author addAuthor(@RequestBody Author author){
        return authorService.save(author.getName(), author.getSurname());
    }

    @PutMapping("/authors/{id}")
    public boolean updateAuthor(@PathVariable String id, @RequestBody Author author){
        return authorService.update(id, author.getName(), author.getSurname());
    }

    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(@PathVariable String id){
        authorService.deleteById(id);
    }
}
