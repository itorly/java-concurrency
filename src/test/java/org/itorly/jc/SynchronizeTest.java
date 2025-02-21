package org.itorly.jc;

import org.itorly.jc.synchronize.SyncAndAnotherMethod;
import org.itorly.jc.synchronize.SynchronizedObj;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SynchronizeTest {

    @Test
    public void testSyncAndOtherMethod() throws InterruptedException {
        SyncAndAnotherMethod t = new SyncAndAnotherMethod();
        Thread thread1 = new Thread(t::one, "第一个线程");
        Thread thread2 = new Thread(t::two, "第二个线程");

        thread1.start();
        thread2.start();

        // 等待两个线程执行完毕
        thread1.join();
        thread2.join();
    }

    @Test
    public void testSynchronizedObj() throws InterruptedException, NoSuchMethodException {
        SynchronizedObj synchronizedObj;

        // Number of threads to create
        int numThreads = 10;
        // Number of increments per thread
        int incrementsPerThread = 1000;
        // Create an array to hold the threads
        Thread[] threads = new Thread[numThreads];

        String[] methodNames = new String[]{
                "add1",
                "add2",
                "add3",
                "add4",
                "add5"
        };

        for (int h = 0; h < methodNames.length; h++) {
            // Reset the count for each method test
            synchronizedObj = new SynchronizedObj();
            // Get the method to call using reflection
            Method method;
            if (methodNames[h].equals("add4") || methodNames[h].equals("add5")) {
                // Static methods
                method = SynchronizedObj.class.getMethod(methodNames[h]);
            } else {
                // Instance methods
                method = SynchronizedObj.class.getMethod(methodNames[h]);
            }

            // Create and start the threads
            for (int i = 0; i < numThreads; i++) {
                int finalH = h;
                SynchronizedObj finalSynchronizedObj = synchronizedObj;
                threads[i] = new Thread(() -> {
                    for (int j = 0; j < incrementsPerThread; j++) {
                        try {
                            if (methodNames[finalH].equals("add4") || methodNames[finalH].equals("add5")) {
                                // Call static methods
                                method.invoke(null);
                            } else {
                                // Call instance methods
                                method.invoke(finalSynchronizedObj);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "Thread-" + methodNames[h] + "-" + i);
                threads[i].start();
            }

            // Wait for all threads to finish
            for (Thread thread : threads) {
                thread.join();
            }

            // Verify the final value of count
            if (!methodNames[h].equals("add4") && !methodNames[h].equals("add5")) {
                int expectedCount = (numThreads * incrementsPerThread); // Initial count is 0
                assertEquals(expectedCount, synchronizedObj.getCount(), "The final count value is incorrect.");
            }
        }

    }
}
