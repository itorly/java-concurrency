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
        CompletableFuture<Integer> firstFutureToCompose = simulateCompletableFuture(1);
        CompletableFuture<String> secondFutureToCompose = firstFutureToCompose.thenCompose(integer -> {
            simulateBusiness();
            return simulateCompletableFuture("Result: " + integer);
        });
        String s = secondFutureToCompose.get();
        System.out.println(s);

        /**
         * thenCombine
         * Perform map operations on two CompletableFuture objects
         */
        CompletableFuture<String> firstFutureToCombine = simulateCompletableFuture("key");
        CompletableFuture<String> secondFutureToCombine = simulateCompletableFuture("value");
        CompletableFuture<String> combinedFuture =
                firstFutureToCombine.thenCombine(secondFutureToCombine, (k, v) -> {
            return k + ":" + v;
        });
        String combinedResult = combinedFuture.get();
        System.out.println("Combined Result: " + combinedResult);

        /**
         * allOf
         * Multiple tasks are aggregated into a task set,
         * and the process can only proceed further after all tasks in the set are completed.
         */
        CompletableFuture<Void> firstFuture = CompletableFuture.runAsync(() -> {
            simulateBusiness();
            System.out.println("First task completed.");
        });
        CompletableFuture<Void> secondFuture = CompletableFuture.runAsync(() -> {
            simulateBusiness();
            System.out.println("Second task completed.");
        });
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(firstFuture, secondFuture);
        Void unused = allOfFuture.get();
        System.out.println("All tasks completed.");

        /**
         * anyOf
         * Multiple tasks can be aggregated into a task set.
         * Once any one of the tasks in the set is completed,
         * the process can proceed to the next step.
         */
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(firstFuture, secondFuture);

        Object o = anyOfFuture.get();
        System.out.println("o: " + o);
        System.out.println("At least one task is done.");

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
