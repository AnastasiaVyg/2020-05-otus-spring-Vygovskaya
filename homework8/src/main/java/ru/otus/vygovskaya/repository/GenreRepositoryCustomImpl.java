package ru.otus.vygovskaya.repository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;
import ru.otus.vygovskaya.domain.Genre;

public class GenreRepositoryCustomImpl implements GenreRepositoryCustom{

    private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public GenreRepositoryCustomImpl(ReactiveMongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<Genre> update(String id, String name) {
        Query query = Query.query(Criteria.where("id").is(new ObjectId(id)));
        Update update = Update.update("name", name);
        return mongoTemplate.findAndModify(query, update, Genre.class);
    }
}
