package org.itorly.jc;

public class MyRunnableTask implements Runnable {

    private static int counterOfRun = 0;

    private final String name;
    private final int delay;

    private final int index;

    public MyRunnableTask(String name, int delay) {
        this.name = name;
        this.delay = delay;
        this.index = ++counterOfRun;
    }

    private String getNameAndIndex() {
        return name + " #" + index;
    }

    @Override
    public void run() {
        System.out.println("Task " + getNameAndIndex() + " started.");
        try {
            // Simulate some work by sleeping
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            System.out.println("Task " + getNameAndIndex() + " was interrupted.");
        }
        System.out.println("Task " + getNameAndIndex() + " completed after " + delay + " milliseconds.");
    }

}
