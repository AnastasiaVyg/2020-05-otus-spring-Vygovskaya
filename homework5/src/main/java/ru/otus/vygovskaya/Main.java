package ru.otus.vygovskaya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.vygovskaya.dao.AuthorDao;
import ru.otus.vygovskaya.domain.Author;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class);
        AuthorDao authorDao = context.getBean(AuthorDao.class);
        authorDao.getAll().stream().forEach(author -> System.out.println(author.getName() + " " + author.getSurname()));
        System.out.println("create id=" + authorDao.create(new Author("Александр", "П")));
    }
}
