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
import ru.otus.vygovskaya.domain.Author;
import ru.otus.vygovskaya.domain.MongoAuthor;
import ru.otus.vygovskaya.repository.AuthorRepository;
import ru.otus.vygovskaya.service.AuthorToMongoService;

import javax.persistence.EntityManagerFactory;

@Configuration
public class AuthorStepConfig {

    private static final int CHUNK_SIZE = 5;

    private final StepBuilderFactory stepBuilderFactory;
    private final AuthorRepository authorRepository;
    private final AuthorToMongoService authorToMongoService;

    @Autowired
    public AuthorStepConfig(StepBuilderFactory stepBuilderFactory, AuthorRepository authorRepository, AuthorToMongoService authorToMongoService){
        this.stepBuilderFactory = stepBuilderFactory;
        this.authorRepository = authorRepository;
        this.authorToMongoService = authorToMongoService;
    }

    @Bean
    @Qualifier("authorReader")
    public JpaPagingItemReader<Author> authorReader(EntityManagerFactory entityManagerFactory){
        return new JpaPagingItemReaderBuilder<Author>()
                .name("authorReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(20)
                .queryString("select a from Author a")
                .build();
    }

    @Bean
    @Qualifier("authorProcessor")
    public ItemProcessor<Author, MongoAuthor> authorProcessor(){
        return authorToMongoService::toMongoAuthor;
    }

    @Bean
    @Qualifier("authorWriter")
    public RepositoryItemWriter<MongoAuthor> authorWriter(){
        return new RepositoryItemWriterBuilder<MongoAuthor>()
                .repository(authorRepository)
                .methodName("insert")
                .build();
    }

    @Bean
    @Qualifier("authorMappingStep")
    public Step stepAuthorMapping(@Qualifier("authorReader") JpaPagingItemReader reader, @Qualifier("authorProcessor") ItemProcessor processor,
                                 @Qualifier("authorWriter") RepositoryItemWriter writer){
        return stepBuilderFactory.get("stepAuthorMapping")
                .chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
