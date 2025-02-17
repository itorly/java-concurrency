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
        CompletableFuture<Integer> firstFuture = simulateIntegerCompletableFuture(1);
        CompletableFuture<String> secondFuture = firstFuture.thenCompose(integer -> {
            simulateBusiness();
            return simulateStringCompletableFuture("Result: " + integer);
        });
        String s = secondFuture.get();
        System.out.println(s);
    }

    public static void simulateBusiness( ) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static CompletableFuture<String> simulateStringCompletableFuture( String string ) {
        return CompletableFuture.supplyAsync(() -> {
            simulateBusiness();
            return string;
        });
    }

    public static CompletableFuture<Integer> simulateIntegerCompletableFuture( Integer integer ) {
        return CompletableFuture.supplyAsync(() -> {
            simulateBusiness();
            return integer;
        });
    }
}
