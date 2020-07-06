package ru.otus.vygovskaya.shell;

import org.springframework.dao.DataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.Comment;
import ru.otus.vygovskaya.service.CommentService;

import java.util.Optional;

@ShellComponent
public class CommentShell {

    private final CommentService commentService;

    public CommentShell(CommentService commentService) {
        this.commentService = commentService;
    }

    @ShellMethod(key = {"createComment", "cC"}, value = "create Comment command")
    public String createComment(@ShellOption(defaultValue = "prety book") String text, @ShellOption(defaultValue = "1") long book_id){
        try {
            Comment comment = commentService.save(text, book_id);
            return "create new " + getCommentInfo(comment.getId(), comment.getText(), comment.getBook());
        } catch (DataAccessException e){
            return "don't create Comment with text - " + text + " book_id - " + book_id;
        }
    }

    @ShellMethod(key = {"getComment", "gC"}, value = "get Comment command")
    public Optional<String> getComment(@ShellOption(defaultValue = "2") long id){
        try {
            Optional<Comment> comment = commentService.getById(id);
            return getCommentInfo(comment);
        } catch (DataAccessException e){
            return Optional.empty();
        }
    }

    @ShellMethod(key = {"deleteComment", "dC"}, value = "delete Comment command")
    public String deleteComment(@ShellOption(defaultValue = "3") long id){
        try {
            int result = commentService.deleteById(id);
            return (result == 1) ? "delete Comment with id " + id : "don't delete Comment with id " + id;
        } catch (DataAccessException e){
            return "don't delete Comment with id " + id + e.toString();
        }
    }

    @ShellMethod(key = {"updateComment", "uC"}, value = "update Comment command")
    public String updateComment(@ShellOption(defaultValue = "1") long id, @ShellOption(defaultValue = "bad") String text){
        try {
            int update = commentService.updateTextById(id, text);
            return update == 1 ? "update Comment with id - " + id : "don't update Comment with id - " + id;
        } catch (DataAccessException e){
            return "don't update Comment with id - " + id;
        }
    }

    @ShellMethod(key = {"getAllComment", "gAC"}, value = "get all Comments command")
    public String getAllComments(){
        try {
            StringBuilder sb = new StringBuilder();
            commentService.getAll().stream().forEach(comment ->
                    sb.append(getCommentInfo(comment.getId(), comment.getText(), comment.getBook())).append("\n"));
            return sb.toString();
        } catch (DataAccessException e){
            return "don't get all Comments";
        }
    }

    private static String getCommentInfo(long id, String text, Book book){
        StringBuilder sb = new StringBuilder();
        sb.append("Comment: id - ").append(id).append(", text - ").append(text).append(", book name - ").append(book.getName());
        return sb.toString();
    }

    private static Optional<String> getCommentInfo(Optional<Comment> optionalComment){
        return optionalComment.map(comment -> getCommentInfo(comment.getId(), comment.getText(), comment.getBook()));
    }
}
