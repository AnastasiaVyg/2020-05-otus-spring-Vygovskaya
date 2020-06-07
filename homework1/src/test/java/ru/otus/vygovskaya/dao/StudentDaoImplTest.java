package ru.otus.vygovskaya.dao;

import org.junit.jupiter.api.Test;
import ru.otus.vygovskaya.domain.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.vygovskaya.utils.TestUtils.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoImplTest {

    @Test
    void create() {
        Student student = new Student(NAME, SURNAME);
        assertThat(student.getName()).isEqualTo(NAME);
        assertThat(student.getSurname()).isEqualTo(SURNAME);
    }
}