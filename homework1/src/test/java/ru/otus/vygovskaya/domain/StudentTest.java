package ru.otus.vygovskaya.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.vygovskaya.utils.TestUtils.NAME;
import static ru.otus.vygovskaya.utils.TestUtils.SURNAME;

class StudentTest {

    private Student student;

    @BeforeEach
    void setUp(){
        student = new Student(NAME, SURNAME);
    }

    @Test
    void getName() {
        assertEquals(NAME, student.getName());
    }

    @Test
    void getSurname() {
        assertEquals(SURNAME, student.getSurname());
    }
}