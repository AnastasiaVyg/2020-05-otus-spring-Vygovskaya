package ru.otus.vygovskaya.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.vygovskaya.dao.QuestionDao;
import ru.otus.vygovskaya.domain.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    private static final String QUESTION_TEXT_1 = "3*2";
    private static final String ANSWER_TEXT_1 = "6";
    private static final String QUESTION_TEXT_2 = "5*2";
    private static final String ANSWER_TEXT_2 = "10";

    @Mock
    private QuestionDao questionDao;

    private QuestionService questionService;

    @BeforeEach
    void setUp(){
        questionService = new QuestionServiceImpl(questionDao);
    }

    @Test
    void getAllQuestions() {
        given(questionDao.getAllQuestions()).willReturn(getQuestions());
        assertThat(questionService.getAllQuestions())
                .isNotEmpty()
                .flatExtracting(Question::getQuestion, Question::getCorrectAnswer)
                .contains(QUESTION_TEXT_1, ANSWER_TEXT_1)
                .contains(QUESTION_TEXT_2, ANSWER_TEXT_2);
    }

    private List<Question> getQuestions(){
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(QUESTION_TEXT_1, ANSWER_TEXT_1));
        questions.add(new Question(QUESTION_TEXT_2, ANSWER_TEXT_2));
        return questions;
    }
}