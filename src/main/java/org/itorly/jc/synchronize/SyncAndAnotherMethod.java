package org.itorly.jc.synchronize;

public class SyncAndAnotherMethod {

    private int count = 0;

    public synchronized void one() {
        System.out.println(Thread.currentThread().getName() + " start one method");
        ++count;
        System.out.println("In one method, count = " + count);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " end one method");
    }

    public void two() {
        System.out.println(Thread.currentThread().getName() + " start two method");
        ++count;
        System.out.println("In two method, count = " + count);
        System.out.println(Thread.currentThread().getName() + " end two method");
    }

}
