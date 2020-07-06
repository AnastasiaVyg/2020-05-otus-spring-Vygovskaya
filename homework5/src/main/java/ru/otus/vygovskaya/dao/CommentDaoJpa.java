package ru.otus.vygovskaya.dao;

import org.springframework.stereotype.Repository;
import ru.otus.vygovskaya.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDaoJpa implements CommentDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() <= 0){
            em.persist(comment);
            return comment;
        }else{
            return em.merge(comment);
        }
    }

    @Override
    public Optional<Comment> getById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public int update(long id, String text) {
        Query query = em.createQuery("update Comment c set c.text = :text where c.id = :id");
        query.setParameter("id", id);
        query.setParameter("text", text);
        return query.executeUpdate();
    }

}
