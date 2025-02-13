package org.itorly.jc.thread;

public class MyThread extends Thread {

    private static int count = 0;

    @Override
    public void run() {
        System.out.println("myThread:" + count++);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            (new MyThread()).start();
        }
    }
}
