package org.itorly.jc.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.Random;

public class ForkJoinDemo {

    static Random random = new Random(0);

    static long random() {
        return random.nextInt(10000);
    }

    public static void main(String[] args) {
        long[] array = new long[2000];
        long expectedSum = 0;
        for (int i = 0; i < array.length; i++) {
            // Fill the array with numbers 1 to 1000
            array[i] = random();
            expectedSum += array[i];
        }
        System.out.println("Expected sum: " + expectedSum);

        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(array, 0, array.length);
        long result = pool.invoke(task);

        System.out.println("Sum: " + result);
    }

    static class SumTask extends RecursiveTask<Long> {
        // Threshold for splitting tasks
        private static final int THRESHOLD = 100;
        private long[] array;
        private int start;
        private int end;

        SumTask(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                // If the task is small enough, compute directly
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                // Split the task into smaller subtasks
                int mid = (start + end) / 2;
                SumTask leftTask = new SumTask(array, start, mid);
                SumTask rightTask = new SumTask(array, mid, end);

                // Fork the subtasks
                leftTask.fork();
                rightTask.fork();

                // Join the results
                return leftTask.join() + rightTask.join();
            }
        }
    }
}
