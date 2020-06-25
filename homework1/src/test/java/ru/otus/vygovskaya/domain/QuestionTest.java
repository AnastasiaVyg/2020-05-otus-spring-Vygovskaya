package ru.otus.vygovskaya.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.vygovskaya.utils.TestUtils.ANSWER_TEXT_1;
import static ru.otus.vygovskaya.utils.TestUtils.QUESTION_TEXT_1;

@SpringBootTest
@DisplayName("Вопрос должен возвращать ")
@ActiveProfiles("test")
class QuestionTest {

    private Question question;

    @BeforeEach
    void setUp(){
        question = new Question(QUESTION_TEXT_1, ANSWER_TEXT_1);
    }

    @Test
    @DisplayName("текст вопроса")
    void getQuestion() {
        assertEquals(QUESTION_TEXT_1, question.getQuestion());
    }

    @Test
    @DisplayName("корректный ответ")
    void getCorrectAnswer() {
        assertEquals(ANSWER_TEXT_1, question.getCorrectAnswer());
    }

}