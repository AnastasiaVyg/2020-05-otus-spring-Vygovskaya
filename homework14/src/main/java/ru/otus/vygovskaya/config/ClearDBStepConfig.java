package ru.otus.vygovskaya.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.vygovskaya.repository.AuthorRepository;
import ru.otus.vygovskaya.repository.BookRepository;
import ru.otus.vygovskaya.repository.GenreRepository;

@Configuration
public class ClearDBStepConfig {

    private final StepBuilderFactory stepBuilderFactory;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ClearDBStepConfig(StepBuilderFactory stepBuilderFactory, AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    @Bean
    public Tasklet clearTables(){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                authorRepository.deleteAll();
                genreRepository.deleteAll();
                bookRepository.deleteAll();
                return RepeatStatus.FINISHED;
            }
        };
    }

    @Bean
    @Qualifier("stepClearDB")
    public Step stepClearDB(){
        return stepBuilderFactory.get("stepClearDB")
                .tasklet(clearTables())
                .build();
    }
}
