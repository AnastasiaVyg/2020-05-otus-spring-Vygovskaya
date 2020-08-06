package ru.otus.vygovskaya.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.data.mongodb")
public class MongoProps {

    private String changelogsPackage;

    public String getChangelogsPackage() {
        return changelogsPackage;
    }

    public void setChangelogsPackage(String changelogsPackage) {
        this.changelogsPackage = changelogsPackage;
    }

}
