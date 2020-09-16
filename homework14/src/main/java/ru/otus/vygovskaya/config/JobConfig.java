package ru.otus.vygovskaya.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

    public static final String IMPORT_JOB_NAME = "importEntities";
    private final JobBuilderFactory jobBuilderFactory;

    @Autowired
    public JobConfig(JobBuilderFactory jobBuilderFactory){
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Job importEntities(@Qualifier("genreMappingStep") Step genreMappingStep, @Qualifier("authorMappingStep") Step authorMappingStep,
                              @Qualifier("bookMappingStep") Step bookMappingStep, @Qualifier("stepClearDB") Step stepClearDB){
        return jobBuilderFactory.get(IMPORT_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(stepClearDB)
                .next(genreMappingStep)
                .next(authorMappingStep)
                .next(bookMappingStep)
                .end()
                .build();
    }
}
