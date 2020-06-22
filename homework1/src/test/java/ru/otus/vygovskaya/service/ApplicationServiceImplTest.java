package ru.otus.vygovskaya.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Question;
import ru.otus.vygovskaya.domain.Student;

import java.util.Locale;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static ru.otus.vygovskaya.utils.TestUtils.*;

@SpringBootTest
@DisplayName("Сервис приложения должен")
@ActiveProfiles("test")
class ApplicationServiceImplTest {

    private static final String TEST_PASS = "Student Aleksey Krasilov passed the test";

    @Autowired
    private ApplicationService applicationService;

    private Student student;

    @MockBean
    private ExaminationService examinationService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private MessageSource messageSource;

    @MockBean
    private IoService ioService;

    InOrder inOrder;

    @BeforeEach
    void setUp(){
        student = new Student(NAME, SURNAME);
        given(studentService.create(NAME, SURNAME)).willReturn(student);
        inOrder = inOrder(ioService);
    }

    @Test
    @DisplayName("создавать студента")
    void login(){
        applicationService.login(SURNAME, NAME);
        verify(studentService, times(1)).create(NAME, SURNAME);
    }

    @Test
    @DisplayName("тестировать студента")
    void testing() {
        given(examinationService.create(student)).willReturn(getExamination());
        given(ioService.nextLine()).willReturn("6").willReturn("10");
        applicationService.login(SURNAME, NAME);
        applicationService.testing();
        for (Question question : getQuestions()) {
            inOrder.verify(ioService, times(1)).println(question.getQuestion());
            inOrder.verify(ioService, times(1)).nextLine();
        }
        verify(messageSource, times(1)).getMessage("pass.test", new String[]{NAME, SURNAME}, Locale.ENGLISH);
        verify(messageSource, never()).getMessage("not.pass.test", new String[]{NAME, SURNAME}, Locale.ENGLISH);
    }

    private Examination getExamination(){
        return new Examination(student, getQuestions(), 2);
    }
}