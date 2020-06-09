package ru.otus.vygovskaya.domain;

import com.google.common.base.Preconditions;

public class Question {
    private final String question;
    private final String correctAnswer;

    public Question(String question, String correctAnswer){
        this.question = Preconditions.checkNotNull(question);
        this.correctAnswer = Preconditions.checkNotNull(correctAnswer);
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        return "Question#" + this.hashCode() + "{" +
                "question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
