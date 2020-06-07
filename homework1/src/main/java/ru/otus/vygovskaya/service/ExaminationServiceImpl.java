package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.dao.ExaminationDao;
import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Student;

public class ExaminationServiceImpl implements ExaminationService{

    private  ExaminationDao examinationDao;
    private QuestionService questionService;
    private int passRate;

    public ExaminationServiceImpl(ExaminationDao examinationDao, QuestionService questionService, int passRate){
        this.examinationDao = examinationDao;
        this.questionService = questionService;
        this.passRate = passRate;
    }

    @Override
    public Examination create(Student student) {
        return examinationDao.createExamination(student, questionService.getAllQuestions(), passRate);
    }
}
