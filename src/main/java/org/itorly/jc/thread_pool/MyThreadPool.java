package org.itorly.jc.thread_pool;

import org.itorly.jc.MyRunnableTask;

import java.util.concurrent.*;

public class MyThreadPool {

    public static void main(String[] args) {
        // Define thread pool parameters
        int corePoolSize = 3;       // Number of core threads
        int maxPoolSize = 4;        // Maximum number of threads
        long keepAliveTime = 10;    // Keep-alive time for idle threads (in seconds)
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2); // Work queue with a capacity of 2

        // Create a custom ThreadPoolExecutor
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,      // Core pool size
                maxPoolSize,       // Maximum pool size
                keepAliveTime,    // Keep-alive time
                TimeUnit.SECONDS,  // Time unit for keep-alive time
                workQueue,         // Work queue
                new ThreadPoolExecutor.CallerRunsPolicy() // Rejection policy
        );

        // Create and submit tasks to the thread pool
        for (int i = 1; i <= 5; i++) {
            // Each task has a different delay
            Runnable task = new MyRunnableTask("Task-" + i, 1000 * i);
            executor.submit(task);
        }

        // Shutdown the executor after all tasks are submitted
        executor.shutdown();

        // Wait for all tasks to complete
        try {
            if (!executor.awaitTermination(20, TimeUnit.SECONDS)) {
                System.out.println("Some tasks are still running. Forcing shutdown.");
                executor.shutdownNow(); // Force shutdown if tasks take too long
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted.");
            executor.shutdownNow();
        }

        System.out.println("All tasks have finished execution.");
    }
}
