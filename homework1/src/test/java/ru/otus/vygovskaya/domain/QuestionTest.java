package ru.otus.vygovskaya.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    private static final String QUESTION_TEXT = "3*2";
    private static final String ANSWER_TEXT = "6";

    @Test
    void getQuestion() {
        Question question = new Question(QUESTION_TEXT, ANSWER_TEXT);
        assertEquals(QUESTION_TEXT, question.getQuestion());
    }

    @Test
    void getCorrectAnswer() {
        Question question = new Question(QUESTION_TEXT, ANSWER_TEXT);
        assertEquals(ANSWER_TEXT, question.getCorrectAnswer());
    }

}