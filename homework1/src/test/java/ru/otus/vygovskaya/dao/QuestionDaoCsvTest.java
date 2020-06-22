package ru.otus.vygovskaya.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.vygovskaya.domain.Question;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Дао для работы с вопросами ")
@SpringBootTest
@ActiveProfiles("test")
class QuestionDaoCsvTest {

    private QuestionDao questionDao;

    @BeforeEach
    void setUp(){
        InputStream inputStream = new ByteArrayInputStream(getQuestionsData().getBytes());
        questionDao = new QuestionDaoCsv(new InputStreamReader(inputStream));
    }

    @Test
    @DisplayName("должен возвращать непустой список вопросов")
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