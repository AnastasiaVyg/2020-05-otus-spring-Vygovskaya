package ru.otus.vygovskaya.repository;

import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;
import ru.otus.vygovskaya.domain.Book;

public class BookRepositoryCustomImpl implements BookRepositoryCustom{

    private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public BookRepositoryCustomImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<Book> update(Book book) {
        Query query = Query.query(Criteria.where("id").is(new ObjectId(book.getId())));
        Update update = Update.update("name", book.getName());
        update.set("year", book.getYear());
        update.set("author", book.getAuthor());
        update.set("genre", book.getGenre());
        return mongoTemplate.findAndModify(query, update, Book.class);
    }

    @Override
    public Mono<DeleteResult> removeBooksByGenreId(String id) {
        Query query = Query.query(Criteria.where("genre.$id").is(new ObjectId(id)));
        return mongoTemplate.remove(query, Book.class);
    }

    @Override
    public Mono<DeleteResult> removeBooksByAuthorId(String id) {
        Query query = Query.query(Criteria.where("author.$id").is(new ObjectId(id)));
        return mongoTemplate.remove(query, Book.class);
    }

}
