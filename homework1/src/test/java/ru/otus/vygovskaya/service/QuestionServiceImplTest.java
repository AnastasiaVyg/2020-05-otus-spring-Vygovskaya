package ru.otus.vygovskaya.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.vygovskaya.dao.QuestionDao;
import ru.otus.vygovskaya.domain.Question;
import ru.otus.vygovskaya.utils.TestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static ru.otus.vygovskaya.utils.TestUtils.*;

@SpringBootTest
@DisplayName("Сервис вопросов должен")
@ActiveProfiles("test")
class QuestionServiceImplTest {

    @MockBean
    private QuestionDao questionDao;

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("возвращать все вопросы")
    void getAllQuestions() {
        given(questionDao.getAllQuestions()).willReturn(TestUtils.getQuestions());
        assertThat(questionService.getAllQuestions())
                .isNotEmpty()
                .flatExtracting(Question::getQuestion, Question::getCorrectAnswer)
                .contains(QUESTION_TEXT_1, ANSWER_TEXT_1)
                .contains(QUESTION_TEXT_2, ANSWER_TEXT_2);
    }

}