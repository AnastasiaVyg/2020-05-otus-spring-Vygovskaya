package ru.otus.vygovskaya.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
        TypedQuery<Book> query = em.createQuery("select b from Book b left join fetch b.comments", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0){
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public int updateNameById(long id, String name) {
        Query query = em.createQuery("update Book b set b.name = :name where b.id = :id");
        query.setParameter("id", id);
        query.setParameter("name", name);
        return query.executeUpdate();
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
