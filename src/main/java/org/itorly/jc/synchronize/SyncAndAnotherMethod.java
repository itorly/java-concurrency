package org.itorly.jc.synchronize;

public class SyncAndAnotherMethod {

    public synchronized void one() {
        System.out.println(Thread.currentThread().getName() + " start one method");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " end one method");
    }

    public void two() {
        System.out.println(Thread.currentThread().getName() + " execute two method");
    }

}
