package ru.otus.vygovskaya.dao;

import ru.otus.vygovskaya.domain.Student;

public interface StudentDao {
    Student create(String name, String surname);
}
