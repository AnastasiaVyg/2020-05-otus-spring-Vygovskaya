package ru.otus.vygovskaya.dao;

import com.google.common.base.Preconditions;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.otus.vygovskaya.domain.Question;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDaoSimple implements QuestionDao {

    private final String questionsFileName;

    public QuestionDaoSimple(String questionsFileName){
        this.questionsFileName = Preconditions.checkNotNull(questionsFileName);
    }

    @Override
    public List<Question> getAllQuestions() {
        try {
            return readQuestions();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<Question> readQuestions() throws URISyntaxException, IOException, CsvException {
        Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource("questions.csv").toURI()));
        CSVReader csvReader = new CSVReader(reader);
        List<Question> questions = csvReader.readAll().stream().map(line -> new Question(line[0], line[1])).collect(Collectors.toList());
        reader.close();
        csvReader.close();
        return questions;
    }
}
