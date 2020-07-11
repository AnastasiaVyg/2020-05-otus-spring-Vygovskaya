package ru.otus.vygovskaya.dao;

import org.springframework.stereotype.Repository;
import ru.otus.vygovskaya.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
    public void deleteById(long id) {
        Optional<Genre> optionalGenre = getById(id);
        optionalGenre.ifPresent(genre -> em.remove(genre));
    }
}
