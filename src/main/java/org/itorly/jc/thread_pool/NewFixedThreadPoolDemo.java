package org.itorly.jc.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewFixedThreadPoolDemo {

    public static void main(String[] args) {
        // Create a thread pool with a fixed number of threads (e.g., 5)
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // Submit tasks to the thread pool
        for (int i = 0; i < 10; i++) {
            Runnable task = new Task(i);
            executorService.submit(task);
        }

        // Shut down the thread pool gracefully
        executorService.shutdown();
    }
}

class Task implements Runnable {
    private int taskId;

    public Task(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("Task " + taskId + " is running on thread " + Thread.currentThread().getName());
        try {
            // Simulate some work by sleeping for a short period
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task " + taskId + " completed.");
    }
}
