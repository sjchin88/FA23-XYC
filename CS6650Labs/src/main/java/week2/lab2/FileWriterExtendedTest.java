package week2.lab2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * One thread writing to the file: The most straightforward way to ensure only one thread writes to
 * the file is to use a BlockingQueue where threads submit their strings and one dedicated thread
 * pulls from this queue and writes to the file.
 * <p>
 * Ascending timestamp order: The best way to do this would be to combine the third approach with a
 * PriorityQueue instead of an ArrayList. However, this means there will be sorting overhead as
 * strings are inserted into the collection. Another method might be to post-process the entire list
 * before writing, but this would require storing everything in memory and then sorting it, which
 * might not be feasible for very large lists.
 *
 * One thread writing, unordered: 6172 ms
 * One thread writing, ordered: 2473 ms
 */
public class FileWriterExtendedTest {

    private static final int THREAD_COUNT = 500;
    private static final int STRINGS_PER_THREAD = 2000; // Or 5000, 10000
    private static final String FILE_NAME = "test_extended.txt";

    public static void main(String[] args) throws Exception {
        System.out.println("One thread writing, unordered: " + testOneThreadWriting(false) + " ms");
        System.out.println("One thread writing, ordered: " + testOneThreadWriting(true) + " ms");
    }

    public static long testOneThreadWriting(boolean ordered) throws Exception {
        long startTime = System.currentTimeMillis();

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT + 1);

        // The writer thread
        executor.submit(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                while (true) {
                    String str = queue.poll(1, TimeUnit.SECONDS);
                    if (str == null) {
                        break;  // No new strings for some time, assume we're done
                    }
                    writer.write(str + "\n");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        // String generating threads
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                List<String> strings = new ArrayList<>();
                for (int j = 0; j < STRINGS_PER_THREAD; j++) {
                    String str =
                        System.currentTimeMillis() + ", " + Thread.currentThread().getId() + ", "
                            + j;
                    strings.add(str);
                }
                if (ordered) {
                    Collections.sort(strings);
                }
                queue.addAll(strings);
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        return System.currentTimeMillis() - startTime;
    }
}
