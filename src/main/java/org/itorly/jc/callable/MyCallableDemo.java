package org.itorly.jc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyCallableDemo {
    public static void main(String[] args) {
        // Create two Callable tasks
        Callable<String> task1 = new MyCallable("Task-1", 1000);
        Callable<String> task2 = new MyCallable("Task-2", 2000);

        // Wrap the Callable tasks in FutureTask
        FutureTask<String> futureTask1 = new FutureTask<>(task1);
        FutureTask<String> futureTask2 = new FutureTask<>(task2);

        // Create and start threads manually
        Thread thread1 = new Thread(futureTask1);
        Thread thread2 = new Thread(futureTask2);

        thread1.start();
        thread2.start();

        // Wait for the results and print them
        try {
            System.out.println(futureTask1.get()); // Blocks until the result is available
            System.out.println(futureTask2.get()); // Blocks until the result is available
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Main thread has finished execution.");
    }
}
