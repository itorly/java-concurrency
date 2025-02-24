package org.itorly.jc.synchronize;

public class ReentrancyOfSynchronized {

    public synchronized void one() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        two();
        System.out.println("one Method, Thread-" + Thread.currentThread().getName() + " end");
    }

    synchronized void two() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("two Method, Thread-" + Thread.currentThread().getName() + " end");
    }

}
