package ru.otus.vygovskaya.rest.dto;

public class BookDto {
    private String id;
    private String name;
    private String authorId;
    private String genreId;
    private int year;

    public BookDto() {
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
