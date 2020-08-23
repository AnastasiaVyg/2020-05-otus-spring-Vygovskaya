package ru.otus.vygovskaya.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;

@ChangeLog(order = "001")
public class InitMongoDbDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "vygovskaya", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initData", author = "vygovskaya", runAlways = true)
    public void initData(MongoTemplate template){
        MongoCollection<Document> genres = template.createCollection("genre");
        IndexOptions indexOptions = new IndexOptions().unique(true);
        genres.createIndex(Indexes.ascending("name"), indexOptions);

        Genre storyGenre = template.save(new Genre("111111111111111111111111", "story"));
        Genre poemGenre = template.save(new Genre("poem"));

        MongoCollection<Document> authors = template.createCollection("author");
        authors.createIndex(Indexes.ascending("name", "surname"), indexOptions);
        Author authorPushkin = template.save(new Author("Alexander", "Pushkin"));
        Author authorLermontov = template.save(new Author( "222222222222222222222222","Mikhail", "Lermontov"));

        MongoCollection<Document> books = template.createCollection("book");
        books.createIndex(Indexes.ascending("name", "author"), indexOptions);
        Book book1 = new Book("Ruslan and Ludmila", authorPushkin, poemGenre, 1820);
        book1.addComment("It is a beautiful");
        book1.addComment("Super");
        template.save(book1);

        template.save(new Book("Story about the fisherman and golden fish", authorPushkin, storyGenre, 1835));
        template.save(new Book("Borodino", authorLermontov, poemGenre, 1837));
    }
}
