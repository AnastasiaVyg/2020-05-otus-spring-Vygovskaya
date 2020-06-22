package ru.otus.vygovskaya.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.vygovskaya.utils.TestUtils.NAME;
import static ru.otus.vygovskaya.utils.TestUtils.SURNAME;

@SpringBootTest
@DisplayName("Студент должен возвращать")
@ActiveProfiles("test")
class StudentTest {

    private Student student;

    @BeforeEach
    void setUp(){
        student = new Student(NAME, SURNAME);
    }

    @Test
    @DisplayName("имя")
    void getName() {
        assertEquals(NAME, student.getName());
    }

    @Test
    @DisplayName("фамилию")
    void getSurname() {
        assertEquals(SURNAME, student.getSurname());
    }
}