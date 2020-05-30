package ru.otus.vygovskaya.dao;

import com.google.common.base.Preconditions;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.core.io.ClassPathResource;
import ru.otus.vygovskaya.domain.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDaoSimple implements QuestionDao {

    private final String questionsFileName;

    public QuestionDaoSimple(String questionsFileName) {
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
