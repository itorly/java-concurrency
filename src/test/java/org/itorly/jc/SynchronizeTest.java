package org.itorly.jc;

import org.itorly.jc.synchronize.SynchronizedObj;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SynchronizeTest {

    @Test
    public void testSynchronizedObj() throws InterruptedException {
        SynchronizedObj synchronizedObj = new SynchronizedObj();

        // Number of threads to create
        int numThreads = 10;
        // Number of increments per thread
        int incrementsPerThread = 1000;
        // Create an array to hold the threads
        Thread[] threads = new Thread[numThreads];

        // Create and start the threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    synchronizedObj.add(); // Call the synchronized method
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        // Verify the final value of count
        int expectedCount = (numThreads * incrementsPerThread); // Initial count is 0
        assertEquals(expectedCount, synchronizedObj.getCount(), "The final count value is incorrect.");


    }
}
