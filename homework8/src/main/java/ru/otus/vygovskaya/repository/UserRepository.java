package ru.otus.vygovskaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.vygovskaya.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByName(String name);
}
