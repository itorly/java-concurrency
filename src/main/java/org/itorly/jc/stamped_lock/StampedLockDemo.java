package org.itorly.jc.stamped_lock;

import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {

    private final StampedLock stampedLock = new StampedLock();
    private int sharedData = 0;

    public void write(int value) {
        long stamp = stampedLock.writeLock(); // Acquire the write lock
        try {
            System.out.println("Writing: " + value);
            sharedData = value;
        } finally {
            stampedLock.unlockWrite(stamp); // Release the write lock
        }
    }

    public int read() {
        long stamp = stampedLock.readLock(); // Acquire the read lock
        try {
            System.out.println("Reading: " + sharedData);
            return sharedData;
        } finally {
            stampedLock.unlockRead(stamp); // Release the read lock
        }
    }

    public int optimisticRead() {
        long stamp = stampedLock.tryOptimisticRead(); // Try optimistic read
        int value = sharedData;
        if (!stampedLock.validate(stamp)) { // Check if the data is still valid
            stamp = stampedLock.readLock(); // Fallback to read lock
            try {
                value = sharedData;
            } finally {
                stampedLock.unlockRead(stamp); // Release the read lock
            }
        }
        System.out.println("Optimistic Read: " + value);
        return value;
    }

    public static void main(String[] args) {
        StampedLockDemo demo = new StampedLockDemo();

        // Writer thread
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                demo.write(i);
                try {
                    Thread.sleep(100); // Simulate some work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Reader thread
        Thread reader = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                demo.read();
                try {
                    Thread.sleep(100); // Simulate some work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Optimistic reader thread
        Thread optimisticReader = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                demo.optimisticRead();
                try {
                    Thread.sleep(100); // Simulate some work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        writer.start();
        reader.start();
        optimisticReader.start();

        try {
            writer.join();
            reader.join();
            optimisticReader.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
