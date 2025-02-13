package org.itorly.jc.anonymous;

public class AnonymousThreadAndRunnable {
    public static void main(String[] args) {

        final int[] count = {0};

        /**
         * Create an object of Thread subclass using anonymous class.
         */
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("Create an object of Thread subclass using anonymous class: " + count[0]++);
            }
        };
        t1.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Create an object of Runnable subclass using anonymous class: " + count[0]++);
            }
        }).start();

        /**
         * 使⽤ lambda 表达式创建 Runnable ⼦类对象
         */
        new Thread(() -> {
            System.out.println("Create an object of Runnable subclass using lambda expression: " + count[0]++);
        }).start();
    }
}
