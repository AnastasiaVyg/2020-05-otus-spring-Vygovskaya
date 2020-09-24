package ru.otus.vygovskaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.vygovskaya.domain.User;

@RepositoryRestResource(path="users")
public interface UserRepository extends MongoRepository<User, String> {

    @RestResource(path = "names", rel = "names")
    User findByName(String name);
}
