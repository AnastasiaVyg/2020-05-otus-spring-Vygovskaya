package ru.otus.vygovskaya;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.vygovskaya.domain.Question;
import ru.otus.vygovskaya.service.QuestionService;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService questionService = context.getBean(QuestionService.class);
        questionService.getAllQuestions().stream().forEach(question -> System.out.println(question.toString()));
        context.close();
    }

}
