package org.itorly.jc.collections;

import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWriteArraySetDemo {
    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();

        // Thread 1: Adds elements to the set
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                set.add("Item " + i);
                System.out.println("Added: Item " + i);
                try {
                    Thread.sleep(100); // Simulate delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Thread 2: Iterates over the set
        Thread reader = new Thread(() -> {
            while (true) {
                System.out.println("Current Set: " + set);
                if (set.size() >= 5) {
                    break; // Stop when all items are added
                }
                try {
                    Thread.sleep(100); // Simulate delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        writer.start();
        reader.start();

        writer.join();
        reader.join();

        System.out.println("Final Set: " + set);
    }
}
