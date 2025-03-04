package org.itorly.jc.thread_pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolBestPractices {
    public static void main(String[] args) {
        // Custom ThreadFactory for better control
        ThreadFactory threadFactory = r -> {
            Thread thread = new Thread(r, "SchedulerThread-" + r.hashCode());
            thread.setPriority(Thread.NORM_PRIORITY);
            thread.setDaemon(false);
            return thread;
        };

        // Create a ScheduledThreadPoolExecutor with a fixed pool size
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4, threadFactory);

        // Define a task with exception handling and logging
        Runnable task = () -> {
            long startTime = System.currentTimeMillis();
            try {
                System.out.println("Task executed by: " + Thread.currentThread().getName());
                // Simulate task execution
                Thread.sleep(1000);
            } catch (Exception e) {
                System.err.println("Task failed: " + e.getMessage());
            } finally {
                long duration = System.currentTimeMillis() - startTime;
                System.out.println("Task completed in " + duration + " ms");
            }
        };

        // Schedule the task with a fixed delay
        scheduler.scheduleWithFixedDelay(task, 2, 3, TimeUnit.SECONDS);

        // Allow the scheduler to run for a while
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Graceful shutdown
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }

        System.out.println("Scheduler shutdown completed.");
    }
}
