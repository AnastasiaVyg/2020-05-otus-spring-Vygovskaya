package ru.otus.vygovskaya.dao;

import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.domain.Student;

@Service
public class StudentDaoImpl implements StudentDao{
    @Override
    public Student create(String name, String surname) {
        return new Student(name, surname);
    }
}
