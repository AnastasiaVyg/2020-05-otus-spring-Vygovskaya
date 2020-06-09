package ru.otus.vygovskaya;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.vygovskaya.service.ApplicationService;

@ComponentScan
@Configuration
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        ApplicationService applicationService = context.getBean(ApplicationService.class);
        applicationService.testing();
        context.close();
    }

}
