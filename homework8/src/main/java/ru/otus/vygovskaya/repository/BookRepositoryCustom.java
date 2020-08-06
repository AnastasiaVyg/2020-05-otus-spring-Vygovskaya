package ru.otus.vygovskaya.repository;

public interface BookRepositoryCustom {

    void removeBooksByGenreId(String id);
    void removeBooksByAuthorId(String id);
}
