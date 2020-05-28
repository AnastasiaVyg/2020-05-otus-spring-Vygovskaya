package ru.otus.vygovskaya.service;

import com.google.common.base.Preconditions;
import ru.otus.vygovskaya.dao.QuestionDao;
import ru.otus.vygovskaya.domain.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao){
        this.dao = Preconditions.checkNotNull(dao);
    }

    @Override
    public List<Question> getAllQuestions() {
        return dao.getAllQuestions();
    }
}
