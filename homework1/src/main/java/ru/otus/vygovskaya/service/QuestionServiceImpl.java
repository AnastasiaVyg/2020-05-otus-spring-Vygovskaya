package ru.otus.vygovskaya.service;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.dao.QuestionDao;
import ru.otus.vygovskaya.domain.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionDao dao;

    @Autowired
    public QuestionServiceImpl(QuestionDao dao){
        this.dao = Preconditions.checkNotNull(dao);
    }

    @Override
    public List<Question> getAllQuestions() {
        return dao.getAllQuestions();
    }
}
