package org.itorly.jc.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    // Define a Semaphore with 3 permits
    private static final Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {
        // Create and start 10 threads
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(new Worker(i));
            thread.start();
        }
    }

    static class Worker implements Runnable {
        private final int id;

        public Worker(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                System.out.println("Worker " + id + " is trying to acquire a permit.");

                // Acquire a permit from the semaphore
                semaphore.acquire();

                System.out.println("Worker " + id + " has acquired a permit and is working.");

                // Simulate work by sleeping for 2 seconds
                Thread.sleep(2000);

                System.out.println("Worker " + id + " is releasing the permit.");

                // Release the permit back to the semaphore
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
