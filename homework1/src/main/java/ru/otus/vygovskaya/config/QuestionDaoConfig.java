package ru.otus.vygovskaya.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.vygovskaya.dao.QuestionDao;
import ru.otus.vygovskaya.dao.QuestionDaoCsv;

@PropertySource("classpath:application.properties")
@Configuration
public class QuestionDaoConfig {

    @Bean
    public QuestionDao questionDao(@Value("${questions.filename}") String filename){
        return new QuestionDaoCsv(filename);
    }
}
