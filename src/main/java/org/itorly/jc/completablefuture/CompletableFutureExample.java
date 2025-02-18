package org.itorly.jc.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * thenRun
         * No parameters, no return value.
         */
        CompletableFuture<Void> futureOfRun = CompletableFuture.supplyAsync(() -> {
            simulateBusiness();
            System.out.println("CompletableFuture is done.");
            return null;
        }).thenRun(() -> System.out.println("Task completed."));

        //  Wait for the task to be completed.
        futureOfRun.get();

        /**
         * thenAccept
         * With parameters but no return value.
         */
        CompletableFuture<Void> futureOfAccept = CompletableFuture.supplyAsync(() -> {
            simulateBusiness();
            return "Hello, CompletableFuture!";
        }).thenAccept(result -> System.out.println("Result: " + result));

        futureOfAccept.get();

        /**
         * thenApply
         * With parameters and return values.
         */
        CompletableFuture<String> futureOfApply = CompletableFuture.supplyAsync(() -> {
            simulateBusiness();
            return "Hello, CompletableFuture!";
        }).thenApply(result -> result.toUpperCase());

        String result = futureOfApply.get();
        System.out.println("Result: " + result);


        /**
         * thenCompose
         * Chain multiple CompletableFuture together in a series.
         */
        CompletableFuture<Integer> firstFuture = simulateCompletableFuture(1);
        CompletableFuture<String> secondFuture = firstFuture.thenCompose(integer -> {
            simulateBusiness();
            return simulateCompletableFuture("Result: " + integer);
        });
        String s = secondFuture.get();
        System.out.println(s);

        /**
         * thenCombine
         * Perform map operations on two CompletableFuture objects
         */
        CompletableFuture<String> thirdFuture = simulateCompletableFuture("key");
        CompletableFuture<String> fourthFuture = simulateCompletableFuture("value");
        CompletableFuture<String> combinedFuture = thirdFuture.thenCombine(fourthFuture, (k, v) -> {
            return k + ":" + v;
        });
        String combinedResult = combinedFuture.get();
        System.out.println("Combined Result: " + combinedResult);


    }

    public static void simulateBusiness( ) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> CompletableFuture<T> simulateCompletableFuture( T t ) {
        return CompletableFuture.supplyAsync(() -> {
            simulateBusiness();
            return t;
        });
    }

}
