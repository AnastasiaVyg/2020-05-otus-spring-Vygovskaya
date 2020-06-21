package ru.otus.vygovskaya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.vygovskaya.config.ApplicationProperties;
import ru.otus.vygovskaya.service.ApplicationService;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        ApplicationProperties applicationProperties = context.getBean(ApplicationProperties.class);
        ApplicationService applicationService = context.getBean(ApplicationService.class);
        applicationService.testing();
    }

}
