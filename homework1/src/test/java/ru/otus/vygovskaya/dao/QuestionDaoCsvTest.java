package ru.otus.vygovskaya.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.vygovskaya.domain.Question;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionDaoCsvTest {

    private QuestionDao questionDao;

    @BeforeEach
    void setUp(){
        InputStream inputStream = new ByteArrayInputStream(getQuestionsData().getBytes());
        questionDao = new QuestionDaoCsv(new InputStreamReader(inputStream));
    }

    @Test
    void getAllQuestions() {
        assertThat(questionDao.getAllQuestions())
                .isNotEmpty()
                .flatExtracting(Question::getQuestion, Question::getCorrectAnswer)
                .contains("15*2", "30")
                .contains("64-10", "54");
    }

    private String getQuestionsData(){
        return "15*2,30\n" + "64-10,54";
    }
}