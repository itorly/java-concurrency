package org.itorly.jc.volatile_keyword;

/**
 * The volatile keyword is used to indicate that a variable's value will be modified by different threads.
 * It ensures that changes made to the variable by one thread are visible to other threads.
 * This is particularly important in multi-threaded environments where threads may cache variables locally,
 * leading to inconsistencies.
 * This demo illustrates how volatile can be used to ensure visibility of changes across threads in a multi-threaded environment.
 */
public class VolatileDemo {
    /**
     * The flag variable is declared as volatile.
     * This ensures that any changes made to flag by one thread are immediately visible to other threads.
     */
    private volatile boolean flag = true;

    public void toggleFlag() {
        flag = !flag;
        System.out.println("Flag toggled to: " + flag);
    }

    public void doWork() {
        while (flag) {
            // Simulate some work
            System.out.println("Working...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Stopped working.");
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileDemo demo = new VolatileDemo();

        // Create a thread that will toggle the flag after some time
        Thread toggleThread = new Thread(() -> {
            try {
                Thread.sleep(2000); // Wait for 2 seconds
                demo.toggleFlag();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Create a thread that will do work while the flag is true
        /**
         * Without the volatile keyword, the workThread might not see the updated value of flag and could continue running indefinitely.
         * The volatile keyword ensures that the workThread sees the updated value of flag as soon as it is changed by the toggleThread.
         */
        Thread workThread = new Thread(demo::doWork);

        // Start both threads
        workThread.start();
        toggleThread.start();

        // Wait for both threads to finish
        workThread.join();
        toggleThread.join();
    }
}
