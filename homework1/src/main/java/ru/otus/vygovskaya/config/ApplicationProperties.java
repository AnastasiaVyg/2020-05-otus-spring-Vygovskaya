package ru.otus.vygovskaya.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private String questionsBasename;

    private int passRate;

    private Locale locale;

    public String getQuestionsBasename() {
        return questionsBasename;
    }

    public void setQuestionsBasename(String questionsBasename) {
        this.questionsBasename = questionsBasename;
    }

    public int getPassRate() {
        return passRate;
    }

    public void setPassRate(int passRate) {
        this.passRate = passRate;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
