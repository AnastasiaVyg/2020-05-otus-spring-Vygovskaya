package ru.otus.vygovskaya.domain;

import java.util.Objects;

public class Genre {
    private long id;
    private final String name;

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}