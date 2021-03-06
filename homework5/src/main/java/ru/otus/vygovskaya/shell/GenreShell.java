package ru.otus.vygovskaya.shell;

import org.springframework.dao.DataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.service.GenreService;

import java.util.Optional;

@ShellComponent
public class GenreShell {

    private final GenreService genreService;

    public GenreShell(GenreService genreService) {
        this.genreService = genreService;
    }

    @ShellMethod(key = {"createGenre", "cG"}, value = "create Genre command")
    public String createGenre(@ShellOption(defaultValue = "documental") String name){
        try {
            Genre genre = genreService.save(name);
            return "create new " + getGenreInfo(genre.getId(), genre.getName());
        } catch (DataAccessException e){
            return "don't create Genre with name - " + name;
        }
    }

    @ShellMethod(key = {"getGenre", "gG"}, value = "get Genre command")
    public Optional<String> getGenre(@ShellOption(defaultValue = "1") long id){
        try {
            Optional<Genre> genre = genreService.getById(id);
            return getGenreInfo(genre);
        } catch (DataAccessException e){
            return Optional.empty();
        }
    }

    @ShellMethod(key = {"deleteGenre", "dG"}, value = "delete Genre command")
    public String deleteGenre(@ShellOption(defaultValue = "5") long id){
        try {
            genreService.deleteById(id);
            return "delete Genre with id " + id;
        } catch (DataAccessException e){
            return "don't delete Genre with id " + id + e.toString();
        }
    }

    @ShellMethod(key = {"updateGenre", "uG"}, value = "update Genre command")
    public String updateGenre(@ShellOption(defaultValue = "1") long id, @ShellOption(defaultValue = "tale") String name){
        try {
            boolean update = genreService.update(id, name);
            return update ? "update " + getGenreInfo(id, name) : "don't update " + getGenreInfo(id, name);
        } catch (DataAccessException e){
            return "don't update " + getGenreInfo(id, name);
        }
    }

    @ShellMethod(key = {"getAllGenre", "gAG"}, value = "get all Genres command")
    public String getAllGenres(){
        try {
            StringBuilder sb = new StringBuilder();
            genreService.getAll().stream().forEach(genre -> sb.append(getGenreInfo(genre.getId(), genre.getName())).append("\n"));
            return sb.toString();
        } catch (DataAccessException e){
            return "don't get all Genres";
        }
    }

    private static String getGenreInfo(long id, String name){
        StringBuilder sb = new StringBuilder();
        sb.append("Genre: id - ").append(id).append(", name - ").append(name);
        return sb.toString();
    }

    private static Optional<String> getGenreInfo(Optional<Genre> optionalGenre){
        return optionalGenre.map(genre -> getGenreInfo(genre.getId(), genre.getName()));
    }
}
