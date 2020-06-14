package ru.otus.vygovskaya.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.vygovskaya.domain.Student;

import static org.mockito.BDDMockito.given;
import static ru.otus.vygovskaya.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    private StudentService studentService;

    @BeforeEach
    void setUp(){
        studentService = new StudentServiceImpl();
    }

    @Test
    void create() {
        Student student = studentService.create(NAME, SURNAME);
        assertThat(student.getName()).isEqualTo(NAME);
        assertThat(student.getSurname()).isEqualTo(SURNAME);
    }
}