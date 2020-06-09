package ru.otus.vygovskaya.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.vygovskaya.dao.QuestionDao;
import ru.otus.vygovskaya.domain.Question;
import ru.otus.vygovskaya.utils.TestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static ru.otus.vygovskaya.utils.TestUtils.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    private QuestionService questionService;

    @BeforeEach
    void setUp(){
        questionService = new QuestionServiceImpl(questionDao);
    }

    @Test
    void getAllQuestions() {
        given(questionDao.getAllQuestions()).willReturn(TestUtils.getQuestions());
        assertThat(questionService.getAllQuestions())
                .isNotEmpty()
                .flatExtracting(Question::getQuestion, Question::getCorrectAnswer)
                .contains(QUESTION_TEXT_1, ANSWER_TEXT_1)
                .contains(QUESTION_TEXT_2, ANSWER_TEXT_2);
    }

}