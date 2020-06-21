package ru.otus.vygovskaya.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.vygovskaya.service.*;

import java.util.Scanner;

@Configuration
public class ApplicationServiceConfig {

    @Bean
    @Autowired
    public ApplicationService applicationService(ExaminationService examinationService, StudentService studentService,
                                                 ApplicationProperties properties, MessageSource messageSource, ScannerService scannerService){
        return new ApplicationServiceImpl(examinationService, studentService, properties, messageSource, scannerService, System.out);
    }
}
