package ru.otus.vygovskaya.dao;

import ru.otus.vygovskaya.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> getAllQuestions();
}
