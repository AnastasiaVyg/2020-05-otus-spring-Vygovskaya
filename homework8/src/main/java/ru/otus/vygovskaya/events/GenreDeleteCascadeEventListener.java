package ru.otus.vygovskaya.events;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.repository.BookRepository;

//@Component
public class GenreDeleteCascadeEventListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;

    @Autowired
    public GenreDeleteCascadeEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
//        super.onBeforeDelete(event);
//        Document source = event.getSource();
//        String id = source.get("_id").toString();
//        bookRepository.removeBooksByGenreId(id);
    }

}
