package ru.otus.vygovskaya.dto;

import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Comment;
import ru.otus.vygovskaya.domain.Genre;

import java.util.ArrayList;
import java.util.List;

public class BookDto {

    private long id;
    private String name;
    private Author author;
    private Genre genre;
    private int year;
    private List<Comment> comments = new ArrayList<>();

    public BookDto(long id, String name, Author author, Genre genre, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
