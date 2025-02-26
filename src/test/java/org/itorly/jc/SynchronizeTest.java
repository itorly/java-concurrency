package org.itorly.jc;

import org.itorly.jc.synchronize.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SynchronizeTest {

    @Test
    public void testExceptionCausingRelease() throws InterruptedException {
        ExceptionCausingRelease ecr = new ExceptionCausingRelease();
        Thread t1 = new Thread(ecr::add, "1");
        t1.start();
        t1.join();

        Thread.sleep(1000);

        Thread t2 = new Thread(ecr::add, "2");
        t2.start();
        t2.join();
    }

    @Test
    public void testReentrancyOfSynchronized() throws InterruptedException {
        ReentrancyOfSynchronized reentrancyOfSynchronized = new ReentrancyOfSynchronized();
        Thread t1 = new Thread(reentrancyOfSynchronized::one, "t1");

        Thread t2 = new Thread(reentrancyOfSynchronized::one, "t2");

        Thread t3 = new Thread(reentrancyOfSynchronized::one, "t3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }

    @Test
    public void testSynchronizedReadAndWrite() throws InterruptedException {
        SynchronizedReadAndWrite bus = new SynchronizedReadAndWrite();
        System.out.println("Initial number of tickets：" + bus.getTicket());
        new Thread(() -> bus.buy(1)).start();
        for (int i = 1; i <= 10; i++) {
            Thread.sleep(500);
            int finalI = i;
            new Thread(() ->
                    System.out.println("The number of times for querying the remaining number of tickets: " +
                            finalI + ", number of tickets: " + bus.getTicket())).start();
        }
    }
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
