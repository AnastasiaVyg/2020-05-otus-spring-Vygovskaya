package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Student;

public interface ExaminationService {

    Examination create(Student student);

}
