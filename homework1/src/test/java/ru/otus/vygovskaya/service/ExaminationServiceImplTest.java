package ru.otus.vygovskaya.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Question;
import ru.otus.vygovskaya.domain.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static ru.otus.vygovskaya.utils.TestUtils.*;

@SpringBootTest
@DisplayName("Сервис экзамена должен")
@ActiveProfiles("test")
class ExaminationServiceImplTest {

    @MockBean
    private QuestionService questionService;

    @Autowired
    private ExaminationService examinationService;

    private Student student;

    @BeforeEach
    void setUp(){
        student = new Student(NAME, SURNAME);
    }

    @Test
    @DisplayName("создавать экзамен для студента с вопросами")
    void createExamination() {
        given(questionService.getAllQuestions()).willReturn(questions);
        Examination examination = examinationService.create(student);
        assertThat(examination.getStudent().getName()).isEqualTo(NAME);
        assertThat(examination.getStudent().getSurname()).isEqualTo(SURNAME);
        assertThat(examination.getPassRate()).isEqualTo(PASS_RATE);
        assertThat(examination.getQuestions())
                .isNotEmpty()
                .flatExtracting(Question::getQuestion, Question::getCorrectAnswer)
                .contains(QUESTION_TEXT_1, ANSWER_TEXT_1)
                .contains(QUESTION_TEXT_2, ANSWER_TEXT_2);
    }
}