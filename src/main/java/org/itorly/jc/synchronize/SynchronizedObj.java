package org.itorly.jc.synchronize;

public class SynchronizedObj {

    private int count = 0;
    private final Object o = new Object();

    public void add1() {
        synchronized (o) {
            System.out.println(Thread.currentThread().getName() + ":" + ++count);
        }
    }

    public void add2() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + ":" + ++count);
        }
    }

    public synchronized void add3() {
        System.out.println(Thread.currentThread().getName() + ":" + ++count);
    }

    public static void add4() {
        synchronized (SynchronizedObj.class) {
            System.out.println(Thread.currentThread().getName());
        }
    }

    public synchronized static void add5() {
        System.out.println(Thread.currentThread().getName());
    }

    public int getCount() {
        return count;
    }
}
