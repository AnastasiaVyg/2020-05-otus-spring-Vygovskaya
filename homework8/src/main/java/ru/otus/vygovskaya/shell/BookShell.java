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
    public String createBook(String authorId, String genreId,
                              @ShellOption(defaultValue = "The Bronze Horseman") String name, @ShellOption(defaultValue = "1833") int year){
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
    public String getBook(String id){
        try {
            Optional<Book> book = bookService.getById(id);
            return getBookInfo(book).orElse("don't get entity");
        } catch (DataAccessException e){
            return "don't get entity";
        }
    }

    @ShellMethod(key = {"deleteBook", "dB"}, value = "delete Book command")
    public String deleteBook(String id){
        try {
            bookService.deleteById(id);
            return "delete Book with id " + id;
        } catch (DataAccessException e){
            return "don't delete Book with id " + id;
        }
    }

    @ShellMethod(key = {"updateBook", "uB"}, value = "update Book command")
    public String updateBook(String id, @ShellOption(defaultValue = "R and L") String name){
        try {
            boolean update = bookService.updateNameById(id, name);
            return update ? "update Book with id " + id : "don't update Book with id " + id;
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
    public String getAllBookByAuthor(String id){
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
    public String getAllBookByGenre(String id){
        try {
            StringBuilder sb = new StringBuilder();
            bookService.getAllByGenreId(id).stream().forEach(book -> sb.append(getBookInfo(book)).append("\n"));
            return sb.toString();
        } catch (DataAccessException e){
            return "don't get all Books by Genre with id - " + id;
        }
    }

    private static Optional<String> getBookInfo(Optional<Book> optionalBook){
        return optionalBook.map(book -> {
            StringBuilder sb = new StringBuilder(getBookInfo(book));
            if (book.getComments() != null) {
                sb.append(", comments -");
                book.getComments().stream().forEach(comment -> sb.append(comment).append("; "));
            }
            return sb.toString();
        });
    }

    private static String getBookInfo(Book book){
        StringBuilder sb = new StringBuilder();
        sb.append("Book: id - ").append(book.getId()).append(", name - ").append(book.getName()).append(", author - ")
                .append(book.getAuthor().getName()).append(" ").append(book.getAuthor().getSurname()).append(", genre - ")
                .append(book.getGenre().getName()).append(", year - ").append(book.getYear());
        return  sb.toString();
    }

}
