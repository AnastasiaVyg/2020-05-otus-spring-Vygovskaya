package ru.otus.vygovskaya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.vygovskaya.service.ExaminationService;
import ru.otus.vygovskaya.service.ExaminationServiceImpl;
import ru.otus.vygovskaya.service.QuestionService;

@Configuration
public class ExaminationServiceConfig {

    @Bean
    public ExaminationService examinationService(QuestionService questionService , ApplicationProperties properties){
        return new ExaminationServiceImpl(questionService, properties.getPassRate());
    }
}
