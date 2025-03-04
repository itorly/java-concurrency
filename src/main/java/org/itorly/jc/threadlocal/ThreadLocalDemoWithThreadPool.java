package org.itorly.jc.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemoWithThreadPool {
    // Define a ThreadLocal variable to store user IDs
    private static final ThreadLocal<Integer> userId = new ThreadLocal<>();

    public static void main(String[] args) {
        // Create a thread pool with 3 threads
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Submit tasks to the thread pool
        for (int i = 1; i <= 5; i++) {
            executorService.submit(new UserTask(i));
        }

        // Shutdown the thread pool after all tasks are submitted
        executorService.shutdown();
    }

    // Task that will be executed by each thread
    static class UserTask implements Runnable {
        private final int id;

        UserTask(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                // Set the user ID in the ThreadLocal variable
                userId.set(id);

                // Simulate some work
                System.out.println("Task " + id + " is running on Thread " + Thread.currentThread().getName());
                Thread.sleep(1000); // Simulate work by sleeping

                // Retrieve the user ID from the ThreadLocal variable
                System.out.println("User ID for Thread " + Thread.currentThread().getName() + " is " + userId.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Clear the ThreadLocal variable to avoid memory leaks
                userId.remove();
                System.out.println("ThreadLocal cleaned up for Thread " + Thread.currentThread().getName());
            }
        }
    }
}
