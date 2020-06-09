package ru.otus.vygovskaya.domain;

import com.google.common.base.Preconditions;

public class Student {

    private final String name;

    private final String surname;

    public Student(String name, String surname){
        this.name = Preconditions.checkNotNull(name);
        this.surname = Preconditions.checkNotNull(surname);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "Student#" +this.hashCode()+"{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
