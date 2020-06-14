package ru.otus.vygovskaya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.vygovskaya.service.ScannerService;
import ru.otus.vygovskaya.service.ScannerServiceImpl;

@Configuration
public class ScannerServiceConfig {

    @Bean
    public ScannerService scannerService(){
        return new ScannerServiceImpl(System.in);
    }
}
