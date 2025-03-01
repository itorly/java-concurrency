package org.itorly.jc.reentrant_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    // Shared resource
    private static int counter = 0;

    // ReentrantLock instance
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        // Create multiple threads that increment the counter
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                incrementCounter();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        // Start the threads
        thread1.start();
        thread2.start();

        // Wait for the threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the final value of the counter
        System.out.println("Final counter value: " + counter);
    }

    private static void incrementCounter() {
        // Acquire the lock
        lock.lock();
        try {
            // Critical section
            counter++;
        } finally {
            // Release the lock in a finally block to ensure it is always released
            lock.unlock();
        }
    }
}
