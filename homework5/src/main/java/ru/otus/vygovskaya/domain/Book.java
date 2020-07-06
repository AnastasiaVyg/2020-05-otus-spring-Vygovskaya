package ru.otus.vygovskaya.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
// Позволяет указать какие связи родительской сущности загружать в одном с ней запросе
@NamedEntityGraph(name = "books-authors-genre-entity-graph",
        attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToOne(targetEntity = Author.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne(targetEntity = Genre.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column(name = "year")
    private int year;

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private List<Comment> comments;

    public Book() {
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
