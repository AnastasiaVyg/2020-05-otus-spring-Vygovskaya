package ru.otus.vygovskaya.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDaoJpa implements AuthorDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() <= 0){
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public int update(long id, String name, String surname) {
        Query query = em.createQuery("update Author a set a.name = :name, a.surname = :surname where a.id = :id");
        query.setParameter("id", id);
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        return query.executeUpdate();
    }
}
