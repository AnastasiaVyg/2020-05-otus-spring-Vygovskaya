package ru.otus.vygovskaya.domain;

import java.util.Objects;

public class Author {

    private long id;
    private String name;
    private String surname;

    public Author(long id, String name, String surname){
        this(name, surname);
        this.id = id;
    }

    public Author(String name, String surname){
        this.name = name;
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return getName().equals(author.getName()) &&
                getSurname().equals(author.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
