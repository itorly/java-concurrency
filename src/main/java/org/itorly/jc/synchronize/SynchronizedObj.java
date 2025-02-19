package org.itorly.jc.synchronize;

public class SynchronizedObj {

    private int count = 0;
    private Object o = new Object();

    public void add() {
        synchronized (o) {
            System.out.println(Thread.currentThread().getName() + ":" + ++count);
        }
    }

    public int getCount() {
        return count;
    }
}
