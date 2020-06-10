package ru.otus.vygovskaya.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import ru.otus.vygovskaya.dao.QuestionDao;
import ru.otus.vygovskaya.dao.QuestionDaoCsv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@PropertySource("classpath:application.properties")
@Configuration
public class QuestionDaoConfig {

    @Bean
    public QuestionDao questionDao(@Value("${questions.filename}") String filename){
        try {
            ClassPathResource r = new ClassPathResource(filename);
            try {
                Reader reader = new InputStreamReader(r.getInputStream());
                return new QuestionDaoCsv(reader);
            } catch (IOException e) {
                throw new RuntimeException("Can't make reader " + r.getPath(), e);
            }
        } catch (Exception e){
            throw new RuntimeException("can't make URI from " + filename, e);
        }
    }
}
