package ru.otus.vygovskaya;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;
import ru.otus.vygovskaya.domain.Box;
import ru.otus.vygovskaya.domain.Television;

import java.util.Collection;

@Component
@MessagingGateway
public interface TvFactory {

    @Gateway(requestChannel = "boxChannel", replyChannel = "tvChannel")
    Television process(Box box);
}
