package ru.otus.vygovskaya.domain;

import java.util.Objects;

public class Book {

    private long id;

    private final String name;

    private final Author author;

    private final Genre genre;

    private final int year;

    public Book(String name, Author author, Genre genre, int year) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.year = year;
    }

    public Book(long id, String name, Author author, Genre genre, int year) {
        this(name, author, genre, year);
        this.id = id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getName(), book.getName()) &&
                Objects.equals(getAuthor(), book.getAuthor()) &&
                Objects.equals(getGenre(), book.getGenre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAuthor(), getGenre());
    }
}
