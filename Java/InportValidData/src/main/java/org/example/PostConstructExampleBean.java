package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Component
public class PostConstructExampleBean {

    @Autowired
    TaskExecutorService taskExecutorService;

    @PostConstruct
    public void init() {
        RunnableTask run = new RunnableTask("Minha runnable");
        Date date = new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10));
        taskExecutorService.scheduleOnce(run, date);
        System.out.println("Chamada unica depois que inicializou: " + date.toString());
    }
}