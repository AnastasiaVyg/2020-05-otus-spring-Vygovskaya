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
import ru.otus.vygovskaya.domain.Book;
import ru.otus.vygovskaya.domain.MongoBook;
import ru.otus.vygovskaya.repository.BookRepository;
import ru.otus.vygovskaya.service.BookToMongoService;

import javax.persistence.EntityManagerFactory;

@Configuration
public class BookStepConfig {

    private static final int CHUNK_SIZE = 5;

    private final StepBuilderFactory stepBuilderFactory;
    private final BookRepository bookRepository;
    private final BookToMongoService bookToMongoService;

    @Autowired
    public BookStepConfig(StepBuilderFactory stepBuilderFactory, BookRepository bookRepository, BookToMongoService bookToMongoService) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.bookRepository = bookRepository;
        this.bookToMongoService = bookToMongoService;
    }

    @Bean
    @Qualifier("bookReader")
    public JpaPagingItemReader<Book> bookReader(EntityManagerFactory entityManagerFactory){
        return new JpaPagingItemReaderBuilder<Book>()
                .name("bookReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(20)
                .queryString("select b from Book b")
                .build();
    }

    @Bean
    @Qualifier("bookProcessor")
    public ItemProcessor<Book, MongoBook> bookProcessor(){
        return bookToMongoService::toMongoBook;
    }

    @Bean
    @Qualifier("bookWriter")
    public RepositoryItemWriter<MongoBook> bookWriter(){
        return new RepositoryItemWriterBuilder<MongoBook>()
                .repository(bookRepository)
                .methodName("insert")
                .build();
    }

    @Bean
    @Qualifier("bookMappingStep")
    public Step stepBookMapping(@Qualifier("bookReader") JpaPagingItemReader reader, @Qualifier("bookProcessor") ItemProcessor processor,
                                 @Qualifier("bookWriter") RepositoryItemWriter writer){
        return stepBuilderFactory.get("stepBookMapping")
                .chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
