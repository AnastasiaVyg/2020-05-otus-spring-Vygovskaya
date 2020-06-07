package ru.otus.vygovskaya.domain;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Examination {

    private final Student student;
    private final List<Question> questions;
    private final int passRate;
    private Map<Question, String> studentAnswers = new HashMap<>();

    public Examination(Student student, List<Question> questions, int passRate){
        this.student = Preconditions.checkNotNull(student, "student can't be null");
        this.questions = Preconditions.checkNotNull(questions, "questions can't be null");
        Preconditions.checkArgument(!questions.isEmpty(), "questions can't be empty");
        this.passRate = passRate;
    }

    public Student getStudent() {
        return student;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getPassRate() {
        return passRate;
    }

    public void addAnswerToMap(Question question, String answer){
        studentAnswers.put(question, answer);
    }

    public boolean checkTest(){
        int trueAnswer = 0;
        for(Map.Entry<Question, String> entry : studentAnswers.entrySet()){
            if (entry.getKey().getCorrectAnswer().equals(entry.getValue())){
                trueAnswer++;
            }
        }
        return trueAnswer >= passRate;
    }
}
