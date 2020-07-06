package ru.otus.vygovskaya.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.vygovskaya.domain.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreDaoJpa implements GenreDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() <= 0){
            em.persist(genre);
            return genre;
        }else{
            return em.merge(genre);
        }
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public int update(long id, String name) {
        Query query = em.createQuery("update Genre g set g.name = :name where g.id = :id");
        query.setParameter("id", id);
        query.setParameter("name", name);
        return query.executeUpdate();
    }
}
