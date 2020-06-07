package ru.otus.vygovskaya.dao;

import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Question;
import ru.otus.vygovskaya.domain.Student;

import java.util.List;

@Service
public class ExaminationDaoImpl implements ExaminationDao{

    @Override
    public Examination createExamination(Student student, List<Question> questions, int passRate) {
        return new Examination(student, questions, passRate);
    }
}
