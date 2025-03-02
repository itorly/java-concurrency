package org.itorly.jc.read_write_lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A ReadWriteLock in Java allows multiple threads to read a shared resource concurrently,
 * but only one thread to write to the resource at a time.
 * This is useful when you have a shared resource that is read frequently but written to infrequently.
 */
public class ReadWriteLockDemo {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private int sharedResource = 0;

    public void readResource() {
        lock.readLock().lock();  // Acquire the read lock
        try {
            System.out.println(Thread.currentThread().getName() + " is reading the resource: " + sharedResource);
            // Simulate reading operation
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();  // Release the read lock
        }
    }

    public void writeResource(int value) {
        lock.writeLock().lock();  // Acquire the write lock
        try {
            System.out.println(Thread.currentThread().getName() + " is writing the resource.");
            sharedResource = value;
            // Simulate writing operation
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();  // Release the write lock
        }
    }

    public static void main(String[] args) {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();

        // Create multiple reader threads
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    demo.readResource();
                }
            }, "Reader-" + i).start();
        }

        // Create a writer thread
        new Thread(() -> {
            for (int j = 0; j < 2; j++) {
                demo.writeResource(j + 1);
            }
        }, "Writer").start();
    }

}
