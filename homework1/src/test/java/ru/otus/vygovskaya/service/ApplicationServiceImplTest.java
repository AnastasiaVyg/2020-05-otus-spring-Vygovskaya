package ru.otus.vygovskaya.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import ru.otus.vygovskaya.config.ApplicationProperties;
import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Student;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static ru.otus.vygovskaya.utils.TestUtils.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {

    private static final String TEST_PASS = "Student Aleksey Krasilov passed the test";

    private ApplicationService applicationService;

    private OutputStream outputStream;

    private Student student;

    @Mock
    private ExaminationService examinationService;

    @Mock
    private StudentService studentService;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private MessageSource messageSource;

    @Mock
    private ScannerService scannerService;

    @BeforeEach
    void setUp(){
        student = new Student(NAME, SURNAME);
        outputStream = new ByteArrayOutputStream();
        applicationService = new ApplicationServiceImpl(examinationService, studentService,
                applicationProperties, messageSource, scannerService, new PrintStream(outputStream));
    }

    @Test
    void testing() {
        given(studentService.create(NAME, SURNAME)).willReturn(student);
        given(examinationService.create(student)).willReturn(getExamination());
        given(applicationProperties.getLocale()).willReturn(Locale.ENGLISH);
        given(messageSource.getMessage("input.surname", null, Locale.ENGLISH)).willReturn("Input surname: ");
        given(messageSource.getMessage("input.name", null, Locale.ENGLISH)).willReturn("Input name: ");
        given(messageSource.getMessage("pass.test", new String[]{student.getName(), student.getSurname()}, Locale.ENGLISH))
                .willReturn(TEST_PASS);
        given(scannerService.nextLine()).willReturn(SURNAME).willReturn(NAME).willReturn("6").willReturn("10");
        applicationService.testing();
        assertThat(outputStream.toString().contains(TEST_PASS)).isEqualTo(true);
    }

    private Examination getExamination(){
        return new Examination(student, getQuestions(), 2);
    }
}