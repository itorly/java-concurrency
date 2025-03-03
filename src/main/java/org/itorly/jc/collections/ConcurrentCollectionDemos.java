package org.itorly.jc.collections;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.*;

public class ConcurrentCollectionDemos {
    // Reusable method for writers (adds items to a collection)
    private static <T> Runnable createWriter(Collection<T> collection, String prefix, int size) {
        return () -> {
            try {
                for (int i = 0; i < size; i++) {
                    T item = (T) (prefix + i);
                    if (collection instanceof BlockingQueue) {
                        ((BlockingQueue<T>) collection).put(item); // BlockingQueue-specific method
                    } else {
                        collection.add(item); // General collection method
                    }
                    System.out.println("Added: " + item);
                    Thread.sleep(100); // Simulate delay
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }

    // Reusable method for readers (reads items from a collection)
    private static <T> Runnable createReader(Collection<T> collection, String description) {
        return () -> {
            try {
                while (true) {
                    System.out.println(description + ": " + collection);
                    if (collection.size() >= 5) {
                        break; // Stop when all items are added
                    }
                    Thread.sleep(100); // Simulate delay
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }

    // Demo for CopyOnWriteArrayList
    public static void copyOnWriteArrayListDemo() throws InterruptedException {
        Collection<String> list = new CopyOnWriteArrayList<>();
        Thread writer = new Thread(createWriter(list, "Item ", 5));
        Thread reader = new Thread(createReader(list, "Current List"));

        writer.start();
        reader.start();

        writer.join();
        reader.join();

        System.out.println("Final List: " + list);
    }

    // Demo for ConcurrentHashMap
    public static void concurrentHashMapDemo() throws InterruptedException {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        Thread writer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    map.put("Key" + i, i);
                    System.out.println("Added: Key" + i + " -> " + i);
                    Thread.sleep(100); // Simulate delay
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread reader = new Thread(createReader(map.entrySet(), "Current Map"));

        writer.start();
        reader.start();

        writer.join();
        reader.join();

        System.out.println("Final Map: " + map);
    }

    // Demo for CopyOnWriteArraySet
    public static void copyOnWriteArraySetDemo() throws InterruptedException {
        Collection<String> set = new CopyOnWriteArraySet<>();
        Thread writer = new Thread(createWriter(set, "Item ", 5));
        Thread reader = new Thread(createReader(set, "Current Set"));

        writer.start();
        reader.start();

        writer.join();
        reader.join();

        System.out.println("Final Set: " + set);
    }

    // Demo for ArrayBlockingQueue
    public static void arrayBlockingQueueDemo() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);
        Thread producer = new Thread(createWriter(queue, "Item ", 5));
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String item = queue.take(); // Blocks if queue is empty
                    System.out.println("Consumed: " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("ArrayBlockingQueue demo completed.");
    }

    // Demo for LinkedBlockingQueue
    public static void linkedBlockingQueueDemo() throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
        Thread producer = new Thread(createWriter(queue, "Item ", 5));
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String item = queue.take(); // Blocks if queue is empty
                    System.out.println("Consumed: " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("LinkedBlockingQueue demo completed.");
    }

    // Demo for LinkedBlockingDeque
    public static void linkedBlockingDequeDemo() throws InterruptedException {
        BlockingDeque<String> deque = new LinkedBlockingDeque<>(5);
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String item = "Item " + i;
                    deque.putLast(item); // Blocks if deque is full
                    System.out.println("Produced: " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String item = deque.takeFirst(); // Blocks if deque is empty
                    System.out.println("Consumed: " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("LinkedBlockingDeque demo completed.");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== CopyOnWriteArrayList Demo ===");
        copyOnWriteArrayListDemo();

        System.out.println("\n=== ConcurrentHashMap Demo ===");
        concurrentHashMapDemo();

        System.out.println("\n=== CopyOnWriteArraySet Demo ===");
        copyOnWriteArraySetDemo();

        System.out.println("\n=== ArrayBlockingQueue Demo ===");
        arrayBlockingQueueDemo();

        System.out.println("\n=== LinkedBlockingQueue Demo ===");
        linkedBlockingQueueDemo();

        System.out.println("\n=== LinkedBlockingDeque Demo ===");
        linkedBlockingDequeDemo();
    }
}
