package ru.otus.vygovskaya.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.vygovskaya.utils.TestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.vygovskaya.utils.TestUtils.*;

@SpringBootTest
@DisplayName("Экзамен должен возвращать ")
@ActiveProfiles("test")
class ExaminationTest {

    private Examination examination;

    @BeforeEach
    void setUp(){
        examination = new Examination(TestUtils.getStudent(), TestUtils.getQuestions(), PASS_RATE);
    }

    @Test
    @DisplayName("конкретного студента")
    void getStudent() {
        assertThat(examination.getStudent()).isEqualToComparingFieldByField(TestUtils.getStudent());
    }

    @Test
    @DisplayName("список вопросов")
    void getQuestions() {
        assertThat(examination.getQuestions())
                .isNotEmpty()
                .flatExtracting(Question::getQuestion, Question::getCorrectAnswer)
                .contains(QUESTION_TEXT_1, ANSWER_TEXT_1)
                .contains(QUESTION_TEXT_2, ANSWER_TEXT_2);
    }

    @Test
    @DisplayName("количество правильных ответов для прохождения теста")
    void getPassRate() {
        assertThat(examination.getPassRate()).isEqualTo(PASS_RATE);
    }

    @Test
    @DisplayName("пройден тест или нет")
    void checkTest() {
        List<Question> questions = examination.getQuestions();
        examination.addAnswerToMap(questions.get(0), "6");
        examination.addAnswerToMap(questions.get(1), "10");
        assertThat(examination.checkTest()).isEqualTo(true);
    }
}