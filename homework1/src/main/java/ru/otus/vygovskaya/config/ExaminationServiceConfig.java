package ru.otus.vygovskaya.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.vygovskaya.service.ExaminationService;
import ru.otus.vygovskaya.service.ExaminationServiceImpl;
import ru.otus.vygovskaya.service.QuestionService;

@PropertySource("classpath:application.properties")
@Configuration
public class ExaminationServiceConfig {

    @Bean
    public ExaminationService examinationService(QuestionService questionService , @Value("${pass.rate}") String passRate){
        return new ExaminationServiceImpl(questionService, Integer.parseInt(passRate));
    }
}
