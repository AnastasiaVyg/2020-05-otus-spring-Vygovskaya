package ru.otus.vygovskaya.dao;

import com.google.common.base.Preconditions;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.otus.vygovskaya.domain.Question;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDaoCsv implements QuestionDao {

    private final Reader reader;

    private List<Question> questions;

    public QuestionDaoCsv(Reader reader) {
        this.reader = Preconditions.checkNotNull(reader);
    }

    @Override
    public List<Question> getAllQuestions() {
        if (questions == null){
            questions = readQuestions();
        }
        return questions;
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
