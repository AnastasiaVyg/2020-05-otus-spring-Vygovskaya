package ru.otus.vygovskaya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import ru.otus.vygovskaya.service.AppService;

@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
public class Main{

    public static void main(String args[]) throws Exception{
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        AppService appService = context.getBean(AppService.class);
        appService.work();
    }
}
