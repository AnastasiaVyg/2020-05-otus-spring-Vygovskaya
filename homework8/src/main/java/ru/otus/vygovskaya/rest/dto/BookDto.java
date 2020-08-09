package ru.otus.vygovskaya.rest.dto;

public class BookDto {
    private final String id;
    private final String name;
    private final String authorId;
    private final String genreId;
    private final int year;

    public BookDto(String id, String name, String authorId, String genreId, int year) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
        this.genreId = genreId;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getGenreId() {
        return genreId;
    }

    public int getYear() {
        return year;
    }
}
