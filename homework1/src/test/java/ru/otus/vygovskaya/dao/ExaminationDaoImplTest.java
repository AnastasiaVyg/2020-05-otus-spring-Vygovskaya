package ru.otus.vygovskaya.dao;

import org.junit.jupiter.api.Test;
import ru.otus.vygovskaya.domain.Examination;
import ru.otus.vygovskaya.domain.Question;
import ru.otus.vygovskaya.domain.Student;
import ru.otus.vygovskaya.utils.TestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.vygovskaya.utils.TestUtils.*;

class ExaminationDaoImplTest {

    @Test
    public void createExamination() {
        Student student = getStudent();
        int passRate = 5;
        Examination examination = new Examination(student, TestUtils.getQuestions(), passRate);
        assertThat(examination.getStudent()).isEqualToComparingFieldByField(student);
        assertThat(examination.getPassRate()).isEqualTo(passRate);
        assertThat(examination.getQuestions())
                .isNotEmpty()
                .flatExtracting(Question::getQuestion, Question::getCorrectAnswer)
                .contains(QUESTION_TEXT_1, ANSWER_TEXT_1)
                .contains(QUESTION_TEXT_2, ANSWER_TEXT_2);
    }
}