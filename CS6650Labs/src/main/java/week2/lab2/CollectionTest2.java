package week2.lab2;


import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * [Single Threaded] Time taken by Hashtable: 24 ms
 * [Single Threaded] Time taken by synchronized HashMap: 10 ms
 * [Single Threaded] Time taken by ConcurrentHashMap: 29 ms
 * [Multi Threaded] Time taken by Hashtable: 81 ms
 * [Multi Threaded] Time taken by synchronized HashMap: 35 ms
 * [Multi Threaded] Time taken by ConcurrentHashMap: 107 ms
 */

public class CollectionTest2 {

    private static final int ELEMENTS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        singleThreadedTest();
        multiThreadedTest();
    }

    public static void singleThreadedTest() {
        // Testing with Hashtable
        Map<Integer, Integer> hashtable = new Hashtable<>();
        long startTimeHashtable = System.currentTimeMillis();
        for (int i = 0; i < ELEMENTS; i++) {
            hashtable.put(i, i);
        }
        long endTimeHashtable = System.currentTimeMillis();

        // Testing with synchronized HashMap
        Map<Integer, Integer> synchronizedHashMap = Collections.synchronizedMap(new HashMap<>());
        long startTimeSyncedHashMap = System.currentTimeMillis();
        for (int i = 0; i < ELEMENTS; i++) {
            synchronizedHashMap.put(i, i);
        }
        long endTimeSyncedHashMap = System.currentTimeMillis();

        // Testing with ConcurrentHashMap
        Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        long startTimeConcurrentHashMap = System.currentTimeMillis();
        for (int i = 0; i < ELEMENTS; i++) {
            concurrentHashMap.put(i, i);
        }
        long endTimeConcurrentHashMap = System.currentTimeMillis();

        // Print the results
        System.out.println(
            "[Single Threaded] Time taken by Hashtable: " + (endTimeHashtable - startTimeHashtable)
                + " ms");
        System.out.println(
            "[Single Threaded] Time taken by synchronized HashMap: " + (endTimeSyncedHashMap
                - startTimeSyncedHashMap) + " ms");
        System.out.println(
            "[Single Threaded] Time taken by ConcurrentHashMap: " + (endTimeConcurrentHashMap
                - startTimeConcurrentHashMap) + " ms");
    }

    public static void multiThreadedTest() throws InterruptedException {
        final int THREADS = 100;

        Map<Integer, Integer> hashtable = new Hashtable<>();
        Map<Integer, Integer> synchronizedHashMap = Collections.synchronizedMap(new HashMap<>());
        Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();

        long startTimeHashtable = System.currentTimeMillis();
        runThreads(hashtable, THREADS);
        long endTimeHashtable = System.currentTimeMillis();

        long startTimeSyncedHashMap = System.currentTimeMillis();
        runThreads(synchronizedHashMap, THREADS);
        long endTimeSyncedHashMap = System.currentTimeMillis();

        long startTimeConcurrentHashMap = System.currentTimeMillis();
        runThreads(concurrentHashMap, THREADS);
        long endTimeConcurrentHashMap = System.currentTimeMillis();

        // Print the results
        System.out.println(
            "[Multi Threaded] Time taken by Hashtable: " + (endTimeHashtable - startTimeHashtable)
                + " ms");
        System.out.println(
            "[Multi Threaded] Time taken by synchronized HashMap: " + (endTimeSyncedHashMap
                - startTimeSyncedHashMap) + " ms");
        System.out.println(
            "[Multi Threaded] Time taken by ConcurrentHashMap: " + (endTimeConcurrentHashMap
                - startTimeConcurrentHashMap) + " ms");
    }

    public static void runThreads(Map<Integer, Integer> map, int numThreads)
        throws InterruptedException {
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < ELEMENTS / numThreads; j++) {
                    map.put(j, j);
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }
    }
}


