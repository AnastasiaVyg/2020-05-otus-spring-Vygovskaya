package ru.otus.vygovskaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.dao.StudentDao;
import ru.otus.vygovskaya.domain.Student;

@Service
public class StudentServiceImpl implements StudentService{

    private StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao){
        this.studentDao = studentDao;
    }

    @Override
    public Student create(String name, String surname) {
        return studentDao.create(name, surname);
    }
}
