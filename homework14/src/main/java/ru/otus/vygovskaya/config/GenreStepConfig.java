package ru.otus.vygovskaya.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.vygovskaya.domain.Genre;
import ru.otus.vygovskaya.domain.MongoGenre;
import ru.otus.vygovskaya.repository.GenreRepository;
import ru.otus.vygovskaya.service.GenreToMongoService;

import javax.persistence.EntityManagerFactory;

@Configuration
public class GenreStepConfig {
    private static final int CHUNK_SIZE = 5;

    private final StepBuilderFactory stepBuilderFactory;
    private final GenreRepository genreRepository;
    private final GenreToMongoService genreToMongoService;

    @Autowired
    public GenreStepConfig(StepBuilderFactory stepBuilderFactory, GenreRepository genreRepository, GenreToMongoService genreToMongoService){
        this.stepBuilderFactory = stepBuilderFactory;
        this.genreRepository = genreRepository;
        this.genreToMongoService = genreToMongoService;
    }

    @Bean
    @Qualifier("genreReader")
    public JpaPagingItemReader<Genre> genreReader(EntityManagerFactory entityManagerFactory){
        return new JpaPagingItemReaderBuilder<Genre>()
                .name("genreReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(20)
                .queryString("select g from Genre g")
                .build();
    }

    @Bean
    @Qualifier("genreProcessor")
    public ItemProcessor<Genre, MongoGenre> genreProcessor(){
        return genreToMongoService::toMongoGenre;
    }

    @Bean
    @Qualifier("genreWriter")
    public RepositoryItemWriter<MongoGenre> genreWriter(){
        return new RepositoryItemWriterBuilder<MongoGenre>()
                .repository(genreRepository)
                .methodName("insert")
                .build();
    }

    @Bean
    @Qualifier("genreMappingStep")
    public Step stepGenreMapping(@Qualifier("genreReader") JpaPagingItemReader reader, @Qualifier("genreProcessor") ItemProcessor processor,
                                 @Qualifier("genreWriter") RepositoryItemWriter writer){
        return stepBuilderFactory.get("stepGenreMapping")
                .chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


}
