package ru.otus.vygovskaya.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.domain.User;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDbDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "vygovskaya", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initData", author = "vygovskaya", runAlways = true)
    public void initData(MongoTemplate template){
        MongoCollection<Document> users = template.createCollection("user");
        IndexOptions indexOptions = new IndexOptions().unique(true);
        users.createIndex(Indexes.ascending("name"), indexOptions);

        List<String> adminRoles = new ArrayList<>();
        adminRoles.add("ADMIN");
        template.save(new User("admin", "$2a$04$4/2kleogmq5MdMMnGL673.//qM.lAYGUfV/PiRnWpCdKk56NN8XcG", adminRoles)); //password
        List<String> userRoles = new ArrayList<>();
        userRoles.add("USER");
        template.save(new User("ivanov", "$2a$04$DSqmLjUInwwMxtvpV88f1uJ2uYiNSpz3VHSE3fv2o6D3AOOhZ98VO", userRoles)); //12345678

        MongoCollection<Document> genres = template.createCollection("genre");
        genres.createIndex(Indexes.ascending("name"), indexOptions);

        Genre storyGenre = template.save(new Genre("story"));
        Genre detectiveGenre = template.save(new Genre("detective"));
        Genre novelGenre = template.save(new Genre("novel"));
        Genre poemGenre = template.save(new Genre("poem"));
        Genre fantasyGenre = template.save(new Genre("fantasy"));

        MongoCollection<Document> authors = template.createCollection("author");
        authors.createIndex(Indexes.ascending("name", "surname"), indexOptions);
        Author authorPushkin = template.save(new Author("Alexander", "Pushkin"));
        Author authorLermontov = template.save(new Author("Mikhail", "Lermontov"));
        Author authorRowling = template.save(new Author("Joanne", "Rowling"));
        Author authorRiordan = template.save(new Author("Rick", "Riordan"));
        Author authorDoyle = template.save(new Author("Arthur", "Conan Doyle"));
        Author authorChristie = template.save(new Author("Agatha", "Christie"));
        Author authorTolstoy = template.save(new Author("Lev", "Tolstoy"));
        Author authorAusten = template.save(new Author("Jane", "Austen"));

        MongoCollection<Document> books = template.createCollection("book");
        books.createIndex(Indexes.ascending("name", "author"), indexOptions);
        Book book1 = new Book("Ruslan and Ludmila", authorPushkin, poemGenre, 1820);
        book1.addComment("It is a beautiful");
        book1.addComment("Super");
        template.save(book1);

        Book book2 = template.save(new Book("Story about the fisherman and golden fish", authorPushkin, storyGenre, 1835));
        Book book3 = template.save(new Book("Borodino", authorLermontov, poemGenre, 1837));
        Book book4 = template.save(new Book("Harry Potter", authorRowling, fantasyGenre, 1997));

        Book book5 = new Book("Percy Jackson", authorRiordan, fantasyGenre, 2005);
        book5.addComment("I did not like");
        template.save(book5);

        Book book6 = template.save(new Book("The adventures of Sherlock Holmes", authorDoyle, detectiveGenre, 1892));
        Book book7 = template.save(new Book("Murder on the Orient Express", authorChristie, detectiveGenre, 1934));
        Book book8 = template.save(new Book("Cards on the Table", authorChristie, detectiveGenre, 1936));
        Book book9 = template.save(new Book("Anna Karenina", authorTolstoy, novelGenre, 1877));
        Book book10 = template.save(new Book("Pride and Prejudice", authorAusten, novelGenre, 1813));
        Book book11 = template.save(new Book("Emma", authorAusten, novelGenre, 1815));
    }
}
