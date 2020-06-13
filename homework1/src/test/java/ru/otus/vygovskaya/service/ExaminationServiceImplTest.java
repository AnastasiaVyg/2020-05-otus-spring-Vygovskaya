package ru.otus.vygovskaya.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Question;
import ru.otus.vygovskaya.domain.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static ru.otus.vygovskaya.utils.TestUtils.*;

@ExtendWith(MockitoExtension.class)
class ExaminationServiceImplTest {

    @Mock
    private QuestionService questionService;

    private ExaminationService examinationService;

    private Student student;

    @BeforeEach
    void setUp(){
        examinationService = new ExaminationServiceImpl(questionService, PASS_RATE);
        student = new Student(NAME, SURNAME);
    }

    @Test
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

    private Examination getExamination(){
        return new Examination(student, questions, PASS_RATE);
    }
}