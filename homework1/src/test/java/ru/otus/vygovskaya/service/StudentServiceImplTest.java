package ru.otus.vygovskaya.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.vygovskaya.domain.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.vygovskaya.utils.TestUtils.NAME;
import static ru.otus.vygovskaya.utils.TestUtils.SURNAME;

@SpringBootTest
@DisplayName("Сервис студентов должен")
@ActiveProfiles("test")
class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;

    @Test
    @DisplayName("создавать студента")
    void create() {
        Student student = studentService.create(NAME, SURNAME);
        assertThat(student.getName()).isEqualTo(NAME);
        assertThat(student.getSurname()).isEqualTo(SURNAME);
    }
}