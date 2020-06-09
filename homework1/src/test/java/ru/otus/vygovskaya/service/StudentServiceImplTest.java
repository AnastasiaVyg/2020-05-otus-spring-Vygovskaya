package ru.otus.vygovskaya.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.vygovskaya.dao.StudentDao;
import ru.otus.vygovskaya.domain.Student;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static ru.otus.vygovskaya.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentDao studentDao;

    private StudentService studentService;

    @BeforeEach
    void setUp(){
        studentService = new StudentServiceImpl(studentDao);
    }

    @Test
    void create() {
        given(studentDao.create(NAME, SURNAME)).willReturn(getStudent());
        Student student = studentService.create(NAME, SURNAME);
        assertThat(student.getName()).isEqualTo(NAME);
        assertThat(student.getSurname()).isEqualTo(SURNAME);
    }
}