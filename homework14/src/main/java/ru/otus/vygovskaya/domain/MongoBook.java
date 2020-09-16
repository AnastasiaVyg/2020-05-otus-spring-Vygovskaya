package ru.otus.vygovskaya.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class MongoBook {

    @Id
    private String id;

    private String name;

    private MongoAuthor author;

    private MongoGenre genre;

    private int year;

    private List<String> comments = new ArrayList<>();

    public MongoBook() {
    }

    public MongoBook(String name, MongoAuthor author, MongoGenre genre, int year) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.year = year;
    }

    public MongoBook(String id, String name, MongoAuthor author, MongoGenre genre, int year) {
        this(name, author, genre, year);
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MongoAuthor getAuthor() {
        return author;
    }

    public MongoGenre getGenre() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(MongoAuthor author) {
        this.author = author;
    }

    public void setGenre(MongoGenre genre) {
        this.genre = genre;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getComments() {
        return comments;
    }

    public void addComment(String comment){
        comments.add(comment);
    }
}
