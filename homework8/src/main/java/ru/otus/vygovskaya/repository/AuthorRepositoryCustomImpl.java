package ru.otus.vygovskaya.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;
import ru.otus.vygovskaya.domain.Author;

public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom{

    private final ReactiveMongoTemplate mongoTemplate;

    public AuthorRepositoryCustomImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<Author> update(String id, String name, String surname) {
        Query query = Query.query(Criteria.where("id").is(new ObjectId(id)));
        Update update = Update.update("name", name);
        update.set("surname", surname);
        return mongoTemplate.findAndModify(query, update, Author.class);
    }
}
