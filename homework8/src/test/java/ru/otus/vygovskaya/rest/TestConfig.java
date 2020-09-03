package ru.otus.vygovskaya.rest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.otus.vygovskaya.domain.User;
import ru.otus.vygovskaya.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public UserRepository mockUserRepository(){
        UserRepository userRepository = mock(UserRepository.class);
        List<String> adminRoles = new ArrayList<>();
        adminRoles.add("ADMIN");
        when(userRepository.findByName("admin")).thenReturn(new User("admin", "password", adminRoles));
        return userRepository;
    }
}
