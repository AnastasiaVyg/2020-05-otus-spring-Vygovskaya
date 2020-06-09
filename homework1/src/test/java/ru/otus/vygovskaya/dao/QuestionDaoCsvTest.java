package ru.otus.vygovskaya.dao;

import org.junit.jupiter.api.Test;
import ru.otus.vygovskaya.domain.Question;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionDaoCsvTest {

    @Test
    void getAllQuestions() {
        QuestionDao questionDao = new QuestionDaoCsv("test.csv");
        assertThat(questionDao.getAllQuestions())
                .isNotEmpty()
                .flatExtracting(Question::getQuestion, Question::getCorrectAnswer)
                .contains("15*2", "30")
                .contains("64-10", "54");
    }
}