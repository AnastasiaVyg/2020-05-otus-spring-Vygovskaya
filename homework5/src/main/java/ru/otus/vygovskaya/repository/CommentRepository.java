package ru.otus.vygovskaya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.vygovskaya.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
