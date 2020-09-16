package ru.otus.vygovskaya.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class MongoGenre {
    @Id
    private String id;

    private String name;

    public MongoGenre() {
    }

    public MongoGenre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public MongoGenre(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return getName().equals(genre.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    public void setName(String name) {
        this.name = name;
    }
}
