package ru.otus.vygovskaya.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.vygovskaya.utils.TestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.vygovskaya.utils.TestUtils.*;

class ExaminationTest {

    private Examination examination;

    @BeforeEach
    void setUp(){
        examination = new Examination(TestUtils.getStudent(), TestUtils.getQuestions(), PASS_RATE);
    }

    @Test
    void getStudent() {
        assertThat(examination.getStudent()).isEqualToComparingFieldByField(TestUtils.getStudent());
    }

    @Test
    void getQuestions() {
        assertThat(examination.getQuestions())
                .isNotEmpty()
                .flatExtracting(Question::getQuestion, Question::getCorrectAnswer)
                .contains(QUESTION_TEXT_1, ANSWER_TEXT_1)
                .contains(QUESTION_TEXT_2, ANSWER_TEXT_2);
    }

    @Test
    void getPassRate() {
        assertThat(examination.getPassRate()).isEqualTo(PASS_RATE);
    }

    @Test
    void checkTest() {
        List<Question> questions = examination.getQuestions();
        examination.addAnswerToMap(questions.get(0), "6");
        examination.addAnswerToMap(questions.get(1), "10");
        assertThat(examination.checkTest()).isEqualTo(true);
    }
}