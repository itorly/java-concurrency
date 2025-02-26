package org.itorly.jc.synchronize;

public class ExceptionCausingRelease {

    private int count = 0;

    public synchronized void add() {
        System.out.println("Thread " + Thread.currentThread().getName() + " start");
        while (true) {
            count++;
            System.out.println("Thread " + Thread.currentThread().getName() + " now count = " + count);
            if (count == 3) {
                throw new NullPointerException("Human-induced Exception");
            }
            if (count == 10) {
                throw new NullPointerException("Test is over.");
            }
        }
    }

}
