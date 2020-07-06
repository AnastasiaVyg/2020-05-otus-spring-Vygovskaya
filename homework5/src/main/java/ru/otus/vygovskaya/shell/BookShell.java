package ru.otus.vygovskaya.shell;

import org.springframework.dao.DataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.service.BookService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ShellComponent
public class BookShell {

    private final BookService bookService;

    public BookShell(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(key = {"createBook", "cB"}, value = "create Book command")
    public String createBook(@ShellOption(defaultValue = "The Bronze Horseman") String name, @ShellOption(defaultValue = "1") long authorId,
                             @ShellOption(defaultValue = "4") long genreId, @ShellOption(defaultValue = "1833") int year){
        try {
            Book book = bookService.save(name, authorId, genreId, year);
            return "create new " + getBookInfo(book);
        } catch (DataAccessException e){
            return "don't create book with name - " + name + ", author id " + authorId + ", genre id " + genreId + " year " + year + e.toString();
        } catch (NoSuchElementException e){
            return "don't create book - does not exist author with id " + authorId + " or does not exist genre with id " +genreId;
        }
    }

    @ShellMethod(key = {"getBook", "gB"}, value = "get Book command")
    public Optional<String> getBook(@ShellOption(defaultValue = "3") long id){
        try {
            Optional<Book> book = bookService.getById(id);
            return getBookInfo(book);
        } catch (DataAccessException e){
            return Optional.empty();
        }
    }

    @ShellMethod(key = {"deleteBook", "dB"}, value = "delete Book command")
    public String deleteBook(@ShellOption(defaultValue = "11") long id){
        try {
            int result = bookService.deleteById(id);
            return (result == 1) ? "delete Book with id " + id : "don't delete Book with id " + id;
        } catch (DataAccessException e){
            return "don't delete Book with id " + id;
        }
    }

    @ShellMethod(key = {"updateBook", "uB"}, value = "update Book command")
    public String updateBook(@ShellOption(defaultValue = "1") long id, @ShellOption(defaultValue = "R and L") String name){
        try {
            int update = bookService.updateNameById(id, name);
            return update == 1 ? "update Book with id " + id : "don't update Book with id " + id;
        } catch (DataAccessException e){
            return "don't update Book with id " + id;
        }
    }

    @ShellMethod(key = {"getAllBook", "gAB"}, value = "get all Books command")
    public String getAllBook(){
        try {
            StringBuilder sb = new StringBuilder();
            List<Book> books = bookService.getAll();
            books.stream().forEach(book -> sb.append(getBookInfo(book)).append("\n"));
            return sb.toString();
        } catch (DataAccessException e){
            return "don't get all Books";
        }
    }

    @ShellMethod(key = {"getAllBookByAuthor", "gABA"}, value = "get all Books by author command")
    public String getAllBookByAuthor(@ShellOption(defaultValue = "6") long id){
        try {
            StringBuilder sb = new StringBuilder();
            bookService.getAllByAuthorId(id).stream().forEach(book -> sb.append(getBookInfo(book)).append("\n"));
            return sb.toString();
        } catch (DataAccessException e) {
            return "don't get all Books by Author with id - " + id;
        } catch (NoSuchElementException e){
                return "don't get books - does not exist author with id " + id;
            }
    }

    @ShellMethod(key = {"getAllBookByGenre", "gABG"}, value = "get all Books by genre command")
    public String getAllBookByGenre(@ShellOption(defaultValue = "3") long id){
        try {
            StringBuilder sb = new StringBuilder();
            bookService.getAllByGenreId(id).stream().forEach(book -> sb.append(getBookInfo(book)).append("\n"));
            return sb.toString();
        } catch (DataAccessException e){
            return "don't get all Books by Genre with id - " + id;
        }
    }

    private static Optional<String> getBookInfo(Optional<Book> optionalBook){
        return optionalBook.map(BookShell::getBookInfo);
    }

    private static String getBookInfo(Book book){
        StringBuilder sb = new StringBuilder();
        sb.append("Book: id - ").append(book.getId()).append(", name - ").append(book.getName()).append(", author - ")
                .append(book.getAuthor().getName()).append(" ").append(book.getAuthor().getSurname()).append(", genre - ")
                .append(book.getGenre().getName()).append(", year - ").append(book.getYear()).append(", comments - ");
        if (book.getComments() != null) {
            book.getComments().stream().forEach(comment -> sb.append(comment.getText()).append("; "));
        }
        return  sb.toString();
    }

}
