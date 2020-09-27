package ru.otus.vygovskaya.service;

import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.domain.Box;
import ru.otus.vygovskaya.domain.Television;

@Service
public class TelevisionService {

    public Television create(Box box) throws Exception {
        System.out.println("creating " + box.getName());
        Thread.sleep(2000);
        System.out.println("creating " + box.getName() + " done");
        return new Television(box.getName());
    }
}
