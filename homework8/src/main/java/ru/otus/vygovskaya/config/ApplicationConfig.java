package ru.otus.vygovskaya.config;

import com.github.cloudyrock.mongock.Mongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class ApplicationConfig {

    @Bean
    public Mongock mongock(MongoProps mongoProps, MongoTemplate mongoTemplate){
        return new SpringMongockBuilder(mongoTemplate, mongoProps.getChangelogsPackage()).build();
    }
}
