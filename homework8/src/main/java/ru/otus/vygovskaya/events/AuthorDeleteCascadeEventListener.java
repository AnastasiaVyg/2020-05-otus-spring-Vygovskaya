package ru.otus.vygovskaya.events;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.repository.BookRepository;

@Component
public class AuthorDeleteCascadeEventListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    @Autowired
    public AuthorDeleteCascadeEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<Author> event) {
        super.onAfterDelete(event);
        Document source = event.getSource();
        String id = source.get("_id").toString();
        bookRepository.removeBooksByAuthorId(id);
    }
}
