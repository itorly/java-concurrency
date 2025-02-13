package org.itorly.jc.runnable;

public class MyRunnable implements Runnable {

    private static int count = 0;

    @Override
    public void run() {
        System.out.println("myRunnable:" + count++);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            (new Thread(new MyRunnable())).start();
        }
    }
}
