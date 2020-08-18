package ru.otus.vygovskaya.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.rest.dto.BookDto;
import ru.otus.vygovskaya.rest.dto.CommentDto;
import ru.otus.vygovskaya.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<BookDto> getAllBooks(){
        List<Book> books = bookService.getAll();
        return bookService.getAll().stream()
                .map(book -> create(book))
                .collect(Collectors.toList());
    }

    @PostMapping("/books")
    public BookDto addBook(@RequestBody BookDto bookDto){
        Book book = bookService.save(bookDto.getName(), bookDto.getAuthorId(), bookDto.getGenreId(), bookDto.getYear());
        return create(book);
    }

    @PutMapping("/books/{id}")
    public boolean updateBook(@PathVariable String id, @RequestBody BookDto bookDto){
        return bookService.updateById(id, bookDto.getName(), bookDto.getAuthorId(), bookDto.getGenreId(), bookDto.getYear());
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable String id){
        bookService.deleteById(id);
    }

    @GetMapping("/books/{id}/comments")
    public List<String> getComments(@PathVariable String id){
        Optional<Book> book = bookService.getById(id);
        return book.map(book1 -> book1.getComments()).orElse(new ArrayList<>());
    }

    @PostMapping("/books/{id}/comments")
    public boolean addComment(@PathVariable String id, @RequestBody CommentDto commentDto){
        return bookService.addComment(id, commentDto.getComment());
    }

    private BookDto create(Book book){
        return new BookDto(book.getId(), book.getName(), book.getAuthor().getId(), book.getGenre().getId(), book.getYear());
    }
}
