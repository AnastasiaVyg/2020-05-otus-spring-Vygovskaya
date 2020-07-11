package ru.otus.vygovskaya.shell;

import org.springframework.dao.DataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.service.AuthorService;

import java.util.Optional;

@ShellComponent
public class AuthorShell {

    private final AuthorService authorService;

    public AuthorShell(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ShellMethod(key = {"createAuthor", "cA"}, value = "create Author command")
    public String createAuthor(@ShellOption(defaultValue = "Boris") String name, @ShellOption(defaultValue = "Akunin") String surname){
        try {
            Author author = authorService.save(name, surname);
            return "create new " + getAuthorInfo(author);
        } catch (DataAccessException e){
            return "don't create Author with name - " + name + " surname - " + surname;
        }
    }

    @ShellMethod(key = {"getAuthor", "gA"}, value = "get Author command")
    public Optional<String> getAuthor(@ShellOption(defaultValue = "1") long id){
        try {
            Optional<Author> author = authorService.getById(id);
            return getAuthorInfo(author);
        } catch (DataAccessException e){
            return Optional.empty();
        }
    }

    @ShellMethod(key = {"deleteAuthor", "dA"}, value = "delete Author command")
    public String deleteAuthor(@ShellOption(defaultValue = "5") long id){
        try {
            authorService.deleteById(id);
            return "delete Author with id " + id;
        } catch (DataAccessException e){
            return "don't delete Author with id " + id +e.toString();
        }
    }

    @ShellMethod(key = {"updateAuthor", "uA"}, value = "update Author command")
    public String updateAuthor(@ShellOption(defaultValue = "8") long id, @ShellOption(defaultValue = "J") String name, @ShellOption(defaultValue = "Aust") String surname){
        try {
            boolean result = authorService.update(id, name, surname);
            return result ? "update " + getAuthorInfo(id, name, surname) : "don't update " + getAuthorInfo(id, name, surname);
        } catch (DataAccessException e){
            return "don't update " + getAuthorInfo(id, name, surname);
        }
    }

    @ShellMethod(key = {"getAllAuthor", "gAA"}, value = "get all Authors command")
    public String getAllAuthors(){
        try {
            StringBuilder sb = new StringBuilder();
            authorService.getAll().stream().forEach(author -> sb.append(getAuthorInfo(author)).append("\n"));
            return sb.toString();
        } catch (DataAccessException e){
            return "don't get all Authors";
        }
    }

    private static Optional<String> getAuthorInfo(Optional<Author> optionalAuthor) {
        return optionalAuthor.map(AuthorShell::getAuthorInfo);
    }

    private static String getAuthorInfo(Author author){
        return getAuthorInfo(author.getId(), author.getName(), author.getSurname());
    }

    private static String getAuthorInfo(long id, String name, String surname){
        StringBuilder sb = new StringBuilder();
        sb.append("Author: id - ").append(id).append(", name - ").append(name).append(", surname - ").append(surname);
        return sb.toString();
    }
}
