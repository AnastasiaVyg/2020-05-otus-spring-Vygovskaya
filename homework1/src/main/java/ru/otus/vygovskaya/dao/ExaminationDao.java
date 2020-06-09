package ru.otus.vygovskaya.dao;

import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Question;
import ru.otus.vygovskaya.domain.Student;

import java.util.List;

public interface ExaminationDao {
    Examination createExamination(Student student, List<Question> questions, int passRate);
}
