package org.itorly.jc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
    public static void main(String[] args) {
        // Create an AtomicInteger with an initial value of 0
        AtomicInteger atomicInteger = new AtomicInteger(0);

        // Increment and get the value atomically
        int incrementedValue = atomicInteger.incrementAndGet();
        System.out.println("After increment: " + incrementedValue); // Output: 1

        // Decrement and get the value atomically
        int decrementedValue = atomicInteger.decrementAndGet();
        System.out.println("After decrement: " + decrementedValue); // Output: 0

        // Add a specific value and get the result atomically
        int addedValue = atomicInteger.addAndGet(5);
        System.out.println("After adding 5: " + addedValue); // Output: 5

        // Compare and set the value atomically
        boolean isUpdated = atomicInteger.compareAndSet(5, 10);
        System.out.println("Compare and set successful: " + isUpdated); // Output: true
        System.out.println("Current value: " + atomicInteger.get()); // Output: 10

        // Get and update the value atomically using a lambda expression
        int updatedValue = atomicInteger.updateAndGet(x -> x * 2);
        System.out.println("After update (multiply by 2): " + updatedValue); // Output: 20

        // Get the current value
        int currentValue = atomicInteger.get();
        System.out.println("Current value: " + currentValue); // Output: 20
    }
}
