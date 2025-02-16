package org.itorly.jc.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureAPIDemo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<String> future = executor.submit(() -> {
            //  Simulate time-consuming operations
            Thread.sleep(2000);
            return "Hello from Future!";
        });

        //  Check whether the task has been completed
        while (!future.isDone()) {
            System.out.println("Waiting for the task to complete...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        //  Check whether the task has been cancelled
        if (! future.isCancelled()) {
            //  Attempt to obtain the result
            try {
                //  Block the main thread until the result is obtained
                String result = future.get();
                System.out.println("Result: " + result);
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error getting result: " + e.getMessage());
            }
        } else {
            System.out.println("Task was cancelled.");
        }

        //  Attempt to cancel the task (in this example, it won't actually change the status as the task has already been completed)
        boolean cancellationResult = future.cancel(true);
        System.out.println("Cancellation attempt result: " + cancellationResult);

        executor.shutdown();
    }

}
