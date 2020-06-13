package ru.otus.vygovskaya.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.vygovskaya.service.ApplicationService;
import ru.otus.vygovskaya.service.ApplicationServiceImpl;
import ru.otus.vygovskaya.service.ExaminationService;
import ru.otus.vygovskaya.service.StudentService;

import java.util.Scanner;

@Configuration
public class ApplicationServiceConfig {

    @Bean
    @Autowired
    public ApplicationService applicationService(ExaminationService examinationService, StudentService studentService){
        return new ApplicationServiceImpl(examinationService, studentService, new Scanner(System.in), System.out);
    }
}
