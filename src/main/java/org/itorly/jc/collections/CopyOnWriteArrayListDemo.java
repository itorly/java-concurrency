package org.itorly.jc.collections;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        // Exception in thread "Thread-1" java.util.ConcurrentModificationException
//        ArrayList<String> list = new ArrayList<>();

        // Thread 1: Adds elements to the list
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                list.add("Item " + i);
                System.out.println("Added: Item " + i);
                try {
                    Thread.sleep(100); // Simulate delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Thread 2: Iterates over the list
        Thread reader = new Thread(() -> {
            while (true) {
                System.out.println("Current List: " + list);
                if (list.size() >= 5) {
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

        System.out.println("Final List: " + list);
    }
}
