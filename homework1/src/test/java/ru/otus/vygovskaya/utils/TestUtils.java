package ru.otus.vygovskaya.utils;

import ru.otus.vygovskaya.domain.Question;
import ru.otus.vygovskaya.domain.Student;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static final String QUESTION_TEXT_1 = "3*2";
    public static final String ANSWER_TEXT_1 = "6";
    public static final String QUESTION_TEXT_2 = "5*2";
    public static final String ANSWER_TEXT_2 = "10";

    public static final String NAME = "Aleksey";
    public static final String SURNAME = "Krasilov";

    public static final int PASS_RATE = 2;

    private TestUtils(){}

    public static final List<Question> questions = getQuestions();

    public static List<Question> getQuestions(){
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(QUESTION_TEXT_1, ANSWER_TEXT_1));
        questions.add(new Question(QUESTION_TEXT_2, ANSWER_TEXT_2));
        return  questions;
    }

    public static Student getStudent(){
        return new Student(NAME, SURNAME);
    }
}
