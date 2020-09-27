package ru.otus.vygovskaya.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.vygovskaya.TvFactory;
import ru.otus.vygovskaya.domain.Box;
import ru.otus.vygovskaya.domain.Television;

@Service
public class AppService {

    private static final String[] TV_NAME = {"Samsung", "LG", "Sony", "Horizont", "BBK", "Erisson", "Funai"};

    private final TvFactory tvFactory;

    @Autowired
    public AppService(TvFactory tvFactory) {
        this.tvFactory = tvFactory;
    }

    public void work() throws Exception{
        for (int i = 0; i< 10; i++) {
            Thread.sleep(1000);
            String name = TV_NAME[RandomUtils.nextInt(0, TV_NAME.length)];
            Box box = new Box(name);
            System.out.println("new create - " + box.getName());
            Television tv = tvFactory.process(box);
            System.out.println("Ready TV - " + tv.getName());
            System.out.println("--------------------------------------");
        }
    }
}
