package ru.otus.vygovskaya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.otus.vygovskaya.dao.QuestionDao;
import ru.otus.vygovskaya.dao.QuestionDaoCsv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Configuration
public class QuestionDaoConfig {

    @Bean
    public QuestionDao questionDao(ApplicationProperties properties){
        try {
            ClassPathResource r = new ClassPathResource(getQuestionsFileName(properties));
            try {
                Reader reader = new InputStreamReader(r.getInputStream());
                return new QuestionDaoCsv(reader);
            } catch (IOException e) {
                throw new RuntimeException("Can't make reader " + r.getPath(), e);
            }
        } catch (Exception e){
            throw new RuntimeException("can't make URI from " + getQuestionsFileName(properties), e);
        }
    }

    private String getQuestionsFileName(ApplicationProperties properties){
        StringBuilder sb = new StringBuilder(properties.getQuestionsBasename()).append("_").append(properties.getLocale().toString())
                .append(".csv");
        return sb.toString();
    }
}
