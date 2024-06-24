package org.example;


import java.util.concurrent.Executor;

class CustomExecutor implements Executor {
    private long delay;

    CustomExecutor (long delay) {
        this.delay = delay;
    }
    public void execute(Runnable r) {

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        r. run();
    }
}