package ru.otus.vygovskaya.shell;

import org.springframework.dao.DataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.service.BookService;

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
            Book book = bookService.create(name, authorId, genreId, year);
            return "create new " + getBookInfo(book);
        } catch (DataAccessException e){
            return "don't create book with name - " + name + ", author id " + authorId + ", genre id " + genreId + " year " + year;
        }
    }

    @ShellMethod(key = {"getBook", "gB"}, value = "get Book command")
    public String getBook(@ShellOption(defaultValue = "3") long id){
        try {
            Book book = bookService.getById(id);
            return getBookInfo(book);
        } catch (DataAccessException e){
            return "don't get Book with id " + id + e.toString();
        }
    }

    @ShellMethod(key = {"deleteBook", "dB"}, value = "delete Book command")
    public String deleteBook(@ShellOption(defaultValue = "11") long id){
        try {
            bookService.deleteById(id);
            return "delete Book with id " + id;
        } catch (DataAccessException e){
            return "don't delete Book with id " + id;
        }
    }

    @ShellMethod(key = {"updateBook", "uB"}, value = "update Book command")
    public String updateBook(@ShellOption(defaultValue = "1") long id, @ShellOption(defaultValue = "Ruslan and Ludmila") String name,
                             @ShellOption(defaultValue = "2") long authorId, @ShellOption(defaultValue = "1") long genreId,
                             @ShellOption(defaultValue = "1820") int year){
        try {
            int update = bookService.update(id, name, authorId, genreId, year);
            return update == 1 ? "update Book with id " + id : "don't update Book with id " + id;
        } catch (DataAccessException e){
            return "don't update Book with id " + id;
        }
    }

    @ShellMethod(key = {"getAllBook", "gAB"}, value = "get all Books command")
    public String getAllBook(){
        try {
            StringBuilder sb = new StringBuilder();
            bookService.getAll().stream().forEach(book -> sb.append(getBookInfo(book)).append("\n"));
            return sb.toString();
        } catch (DataAccessException e){
            return "don't get all Books";
        }
    }

    private static String getBookInfo(Book book){
        StringBuilder sb = new StringBuilder();
        sb.append("Book: id - ").append(book.getId()).append(", name - ").append(book.getName()).append(", author - ")
                .append(book.getAuthor().getName()).append(" ").append(book.getAuthor().getSurname()).append(", genre - ")
                .append(book.getGenre().getName()).append(", year - ").append(book.getYear());
        return  sb.toString();
    }

}
