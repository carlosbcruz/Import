package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TaskExecutorService {

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    public TaskExecutorService(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    public void scheduleOnce(Runnable runnable, Date runAt) {
        threadPoolTaskScheduler.schedule(runnable, runAt);
    }
}