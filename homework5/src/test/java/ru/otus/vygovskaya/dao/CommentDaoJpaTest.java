package ru.otus.vygovskaya.dao;

import liquibase.pro.packaged.c;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Comment;
import ru.otus.vygovskaya.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями")
@DataJpaTest
@Import({CommentDaoJpa.class})
class CommentDaoJpaTest {

    private static final long FIRST_COMMENT_ID = 1L;

    private static final String COMMENT_1 = "It is a beautiful";
    private static final String COMMENT_2 = "Super";
    private static final String COMMENT_3 = "don't like";

    private static final long BOOK_ID = 2L;

    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;

    private static final String BOOK_NAME = "The Golden Cockerel";
    private static final int YEAR = 2000;
    private static final String AUTHOR_NAME = "Boris";
    private static final String AUTHOR_SURNAME = "Akunin";
    private static final String GENRE = "documental";

    @Autowired
    private CommentDaoJpa commentDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен возвращать все комментарии")
    @Test
    void getAll() {
        List<Comment> comments = commentDaoJpa.getAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS)
                .flatExtracting(Comment::getText)
                .contains(COMMENT_1)
                .contains(COMMENT_2);
    }

    @DisplayName("должен создавать комментарий в БД, а потом возвращать его")
    @Test
    void save() {
        Author author = new Author(0, AUTHOR_NAME, AUTHOR_SURNAME);
        Genre genre = new Genre(0, GENRE);
        Book book = new Book(0, BOOK_NAME, author, genre, YEAR);

        Comment comment = new Comment(0, COMMENT_3, book);
        commentDaoJpa.save(comment);
        assertThat(comment.getId()).isGreaterThan(0);
        Comment expectedComment = em.find(Comment.class, comment.getId());
        assertThat(comment).isEqualToComparingFieldByField(expectedComment);
    }

    @DisplayName("должен возвращать комментарий из БД по его идентификатору")
    @Test
    void getById() {
        Optional<Comment> actualComment = commentDaoJpa.getById(FIRST_COMMENT_ID);
        Comment expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(actualComment).isPresent().get()
                .isEqualToComparingFieldByField(expectedComment);
    }

    @DisplayName("должен удалять комментарий из БД по id")
    @Test
    void deleteById() {
        Comment comment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(comment).isNotNull();
        em.detach(comment);

        commentDaoJpa.deleteById(FIRST_COMMENT_ID);
        Comment deletedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(deletedComment).isNull();
    }

    @DisplayName("должен обновлять текст комментария в БД по идентификатору")
    @Test
    void update() {
        String newText = "New Comment";

        Comment comment = em.find(Comment.class, FIRST_COMMENT_ID);
        String oldText = comment.getText();
        em.detach(comment);

        commentDaoJpa.update(FIRST_COMMENT_ID, newText);
        Comment updatedComment = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(updatedComment.getText()).isNotEqualTo(oldText).isEqualTo(newText);
    }
}