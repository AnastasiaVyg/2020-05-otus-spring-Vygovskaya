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

    private final Reader reader;

    public QuestionDaoCsv(Reader reader) {
        this.reader = Preconditions.checkNotNull(reader);
    }

    @Override
    public List<Question> getAllQuestions() {
        return readQuestions();
    }

    private List<Question> readQuestions()  {
        try (CSVReader csvReader = new CSVReader(reader)){
            List<Question> questions = csvReader.readAll().stream()
                        .map(line -> new Question(line[0], line[1]))
                        .collect(Collectors.toList());
                return questions;
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Can't read file ", e);
        }
    }
}
