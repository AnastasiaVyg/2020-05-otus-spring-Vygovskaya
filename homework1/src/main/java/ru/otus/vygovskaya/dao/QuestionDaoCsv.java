package ru.otus.vygovskaya.dao;

import com.google.common.base.Preconditions;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.core.io.ClassPathResource;
import ru.otus.vygovskaya.domain.Question;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDaoCsv implements QuestionDao {

    private final String questionsFileName;

    public QuestionDaoCsv(String questionsFileName) {
        this.questionsFileName = Preconditions.checkNotNull(questionsFileName);
    }

    @Override
    public List<Question> getAllQuestions() {
        return readQuestions();
    }

    private List<Question> readQuestions()  {
         try {
             ClassPathResource r = new ClassPathResource(questionsFileName);
            try (Reader reader = new InputStreamReader(r.getInputStream(), StandardCharsets.UTF_8);
                 CSVReader csvReader = new CSVReader(reader)) {
                List<Question> questions = csvReader.readAll().stream()
                        .map(line -> new Question(line[0], line[1]))
                        .collect(Collectors.toList());
                return questions;
            } catch (IOException | CsvException e) {
                throw new RuntimeException("Can't read file " + r.getPath(), e);
            }
        } catch (Exception e) {
            throw new RuntimeException("can't make URI from " + questionsFileName, e);
        }
    }
}
