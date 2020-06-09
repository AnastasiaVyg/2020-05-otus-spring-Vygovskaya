package ru.otus.vygovskaya.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.vygovskaya.utils.TestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.vygovskaya.utils.TestUtils.*;

class QuestionTest {

    private Question question;

    @BeforeEach
    void setUp(){
        question = new Question(QUESTION_TEXT_1, ANSWER_TEXT_1);
    }

    @Test
    void getQuestion() {
        assertEquals(QUESTION_TEXT_1, question.getQuestion());
    }

    @Test
    void getCorrectAnswer() {
        assertEquals(ANSWER_TEXT_1, question.getCorrectAnswer());
    }

}