package ru.otus.vygovskaya.repository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.vygovskaya.domain.Book;

public class BookRepositoryCustomImpl implements BookRepositoryCustom{

    private final MongoTemplate mongoTemplate;

    @Autowired
    public BookRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void removeBooksByGenreId(String id) {
        Query query = Query.query(Criteria.where("genre._id").is(new ObjectId(id)));
        mongoTemplate.remove(query, Book.class);
    }

    @Override
    public void removeBooksByAuthorId(String id) {
        Query query = Query.query(Criteria.where("author._id").is(new ObjectId(id)));
        mongoTemplate.remove(query, Book.class);
    }

}
