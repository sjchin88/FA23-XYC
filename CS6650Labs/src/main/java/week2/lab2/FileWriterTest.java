package week2.lab2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Approach 1 Direct writes time: 10453 ms
 * Approach 2 Buffered thread writes time: 708 ms
 * Approach 3 Collect all in shared collection time: 2459 ms
 */
public class FileWriterTest {

    private static final int THREAD_COUNT = 500;
    private static final int STRINGS_PER_THREAD = 1000;
    private static final String FILE_NAME = "test.txt";

    public static void main(String[] args) throws Exception {
        System.out.println("Approach 1 time: " + testApproach1() + " ms");
        System.out.println("Approach 2 time: " + testApproach2() + " ms");
        System.out.println("Approach 3 time: " + testApproach3() + " ms");
    }

    // Approach 1: Direct writes
    public static long testApproach1() throws Exception {
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                    for (int j = 0; j < STRINGS_PER_THREAD; j++) {
                        String str =
                            System.currentTimeMillis() + ", " + Thread.currentThread().getId()
                                + ", " + j + "\n";
                        writer.write(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        return System.currentTimeMillis() - startTime;
    }

    // Approach 2: Buffered thread writes
    public static long testApproach2() throws Exception {
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < STRINGS_PER_THREAD; j++) {
                    builder.append(System.currentTimeMillis()).append(", ")
                        .append(Thread.currentThread().getId()).append(", ")
                        .append(j).append("\n");
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                    writer.write(builder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        return System.currentTimeMillis() - startTime;
    }

    // Approach 3: Collect all in shared collection
    public static long testApproach3() throws Exception {
        long startTime = System.currentTimeMillis();
        List<String> sharedCollection = Collections.synchronizedList(new ArrayList<>());

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                for (int j = 0; j < STRINGS_PER_THREAD; j++) {
                    String str =
                        System.currentTimeMillis() + ", " + Thread.currentThread().getId() + ", "
                            + j;
                    sharedCollection.add(str);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String str : sharedCollection) {
                writer.write(str + "\n");
            }
        }

        return System.currentTimeMillis() - startTime;
    }
}

