package org.itorly.jc.reentrant_lock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean flag = false;

    public void await() {
        lock.lock();
        try {
            while (!flag) {
                System.out.println("Waiting for condition...");
                condition.await(); // Releases the lock and waits
            }
            System.out.println("Condition met, proceeding...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void signal() {
        lock.lock();
        try {
            flag = true;
            condition.signal(); // Wakes up one waiting thread
            System.out.println("Condition signaled...");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionDemo demo = new ConditionDemo();

        Thread waitingThread = new Thread(demo::await);
        waitingThread.start();

        // Simulate some work before signaling
        Thread.sleep(2000);

        Thread signalingThread = new Thread(demo::signal);
        signalingThread.start();

        waitingThread.join();
        signalingThread.join();
    }
}
