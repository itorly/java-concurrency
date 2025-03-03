package org.itorly.jc.collections;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // Thread 1: Adds elements to the map
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                map.put("Key" + i, i);
                System.out.println("Added: Key" + i + " -> " + i);
                try {
                    Thread.sleep(100); // Simulate delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Thread 2: Reads elements from the map
        Thread reader = new Thread(() -> {
            while (true) {
                System.out.println("Current Map: " + map);
                if (map.size() >= 5) {
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

        System.out.println("Final Map: " + map);
    }
}
