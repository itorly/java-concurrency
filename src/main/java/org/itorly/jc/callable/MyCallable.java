package org.itorly.jc.callable;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<String> {

    private final String name;
    private final int delay;

    public MyCallable(String name, int delay) {
        this.name = name;
        this.delay = delay;
    }

    @Override
    public String call() throws Exception {
        // Simulate some work by sleeping
        Thread.sleep(delay);
        return "Task " + name + " completed after " + delay + " milliseconds";
    }
}
