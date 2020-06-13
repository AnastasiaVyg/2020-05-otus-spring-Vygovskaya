package ru.otus.vygovskaya.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Student;

import java.io.*;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static ru.otus.vygovskaya.utils.TestUtils.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {

    private static final String TEST_STRING = "Student Aleksey Krasilov passed the test";

    private ApplicationService applicationService;

    private OutputStream outputStream;

    private Student student;

    @Mock
    private ExaminationService examinationService;

    @Mock
    private StudentService studentService;

    @BeforeEach
    void setUp(){
        InputStream inputStream = new ByteArrayInputStream(getInputData().getBytes());
        student = new Student(NAME, SURNAME);
        outputStream = new ByteArrayOutputStream();
        applicationService = new ApplicationServiceImpl(examinationService, studentService, new Scanner(inputStream), new PrintStream(outputStream));
    }

    @Test
    void testing() {
        given(studentService.create(NAME, SURNAME)).willReturn(student);
        given(examinationService.create(student)).willReturn(getExamination());
        applicationService.testing();
        assertThat(outputStream.toString().contains(TEST_STRING)).isEqualTo(true);
    }

    private String getInputData(){
        StringBuilder sb = new StringBuilder(SURNAME).append("\n").append(NAME)
                .append("\n").append("6\n").append("10");
        return sb.toString();
    }

    private Examination getExamination(){
        return new Examination(student, getQuestions(), 2);
    }
}