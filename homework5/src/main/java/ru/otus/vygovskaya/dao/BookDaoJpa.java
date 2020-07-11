package ru.otus.vygovskaya.dao;

import org.springframework.stereotype.Repository;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoJpa implements BookDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("books-authors-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (!em.contains(book)){
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> getById(long id) {
         Optional<Book> optionalBook = Optional.ofNullable(em.find(Book.class, id));
         return optionalBook;
    }

    @Override
    public void deleteById(long id) {
        Optional<Book> optionalBook = getById(id);
        optionalBook.ifPresent(book -> em.remove(book));
    }

    @Override
    public List<Book> getAllByAuthorId(Author author) {
        EntityGraph<?> entityGraph = em.getEntityGraph("books-authors-genre-entity-graph");
        Query query = em.createQuery("select b from Book b where b.author = :author");
        query.setParameter("author", author);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Book> getAllByGenreId(Genre genre) {
        EntityGraph<?> entityGraph = em.getEntityGraph("books-authors-genre-entity-graph");
        Query query = em.createQuery("select b from Book b where b.genre = :genre");
        query.setParameter("genre", genre);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

}
