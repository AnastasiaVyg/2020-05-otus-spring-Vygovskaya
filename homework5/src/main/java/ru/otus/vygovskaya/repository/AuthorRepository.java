package ru.otus.vygovskaya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.vygovskaya.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
