package week2.lab2;

import java.util.ArrayList;
import java.util.List;

/**
 * 1000 threads
 * Counter Value: 10000
 * Time taken: 79 ms
 * 1000000 threads
 * Counter Value: 10000000
 * Time taken: 65416 ms
 */

public class CounterMain {
    public static void main(String[] args) throws InterruptedException {
        int numOfThreads = 1000000;

        Counter counter = new Counter();

        List<Thread> threads = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        // Start the threads
        for (int i = 0; i < numOfThreads; i++) {
            Thread thread = new Thread(new CounterThread(counter));
            threads.add(thread);
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Counter Value: " + counter.getCount());
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }
}

