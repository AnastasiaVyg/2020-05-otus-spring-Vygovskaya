package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.domain.Student;

@Service
public class StudentServiceImpl implements StudentService{
//
//    @Autowired
//    public StudentServiceImpl(StudentDao studentDao){
//        this.studentDao = studentDao;
//    }

    @Override
    public Student create(String name, String surname) {
        return new Student(name, surname);
    }
}
