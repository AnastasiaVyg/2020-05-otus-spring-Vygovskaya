package ru.otus.vygovskaya.actuators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.vygovskaya.repository.BookRepository;

@Component
public class BooksHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Autowired
    public BooksHealthIndicator(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @Override
    public Health health() {
        boolean zeroBooks = bookRepository.findAll().size() == 0;
        if (zeroBooks) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Удалены все книги!!!")
                    .build();
        } else {
            return Health.up().withDetail("message", "Все отлично, книги есть!").build();
        }
    }
}
