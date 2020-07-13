package ru.otus.vygovskaya.dao;

import org.springframework.stereotype.Repository;
import ru.otus.vygovskaya.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public void deleteById(long id) {
        Optional<Comment> optionalComment = getById(id);
        optionalComment.ifPresent(comment -> em.remove(comment));
    }

}
