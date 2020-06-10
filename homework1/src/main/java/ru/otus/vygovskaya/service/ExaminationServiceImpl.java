package ru.otus.vygovskaya.service;

import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Student;

public class ExaminationServiceImpl implements ExaminationService{

    private QuestionService questionService;
    private int passRate;

    public ExaminationServiceImpl(QuestionService questionService, int passRate){
        this.questionService = questionService;
        this.passRate = passRate;
    }

    @Override
    public Examination create(Student student) {
        return new Examination(student, questionService.getAllQuestions(), passRate);
    }
}
