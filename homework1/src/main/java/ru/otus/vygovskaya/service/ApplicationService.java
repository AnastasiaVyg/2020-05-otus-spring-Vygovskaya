package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Student;

public interface ApplicationService {

    Student login(String surname, String name);

    void testing();

}
