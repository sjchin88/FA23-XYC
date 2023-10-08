package client2;

import static client2.WriteCSV.writeLogsToCSV;

import java.util.ArrayList;
import java.util.List;

public class Main2 {

    public static void main(String[] args) throws InterruptedException {
        String publicIPv4 = "35.85.222.229";
        String javaServerAddr = "http://" + publicIPv4 + ":8080/AlbumServlet_war";
//        String javaServerAddr = "http://localhost:8080/AlbumServlet_war_exploded/";
        String goServerAddr = "http://" + publicIPv4 + ":3000";

//        test(1, 1, 0, javaServerAddr);
        System.out.println();
        test(10, 10, 2, javaServerAddr);
        test(10, 20, 2, javaServerAddr);
        test(10, 30, 2, javaServerAddr);
        test(10, 10, 2, goServerAddr);
        test(10, 20, 2, goServerAddr);
        test(10, 30, 2, goServerAddr);

        // Consolidate logs from all threads
        List<RequestLog> allLogs = new ArrayList<>(ClientThread.requestLogs);

        // Write logs to CSV
        writeLogsToCSV(allLogs);

        // Print statistics
        Statistics.printStats(allLogs);
    }

    public static void test(int threadGroupSize, int numThreadGroups, long delay, String IPAddr)
        throws InterruptedException {
        // initialization phase : 10 threads with 100 calls each
        Thread[] initializationPhase = new Thread[10];
        for (int i = 0; i < 10; i++) {
            initializationPhase[i] = new Thread(new ClientThread(100, IPAddr));
            initializationPhase[i].start();
        }
        try {
            for (int i = 0; i < 10; i++) {
                initializationPhase[i].join();
            }
        } catch (InterruptedException e) {
        }

        // Take a startTime timestamp
        long startTime = System.currentTimeMillis();

        Thread[] groups = new Thread[numThreadGroups];
        for (int i = 0; i < numThreadGroups; i++) {
            groups[i] = new Thread(() -> {
                Thread[] currGroup = new Thread[threadGroupSize];
                for (int j = 0; j < threadGroupSize; j++) {
                    currGroup[j] = new Thread(new ClientThread(1000, IPAddr));
                    currGroup[j].start();
                }
                try {
                    for (int j = 0; j < threadGroupSize; j++) {
                        currGroup[j].join();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            groups[i].start();
            Thread.sleep(delay * 1000);
        }
        try {
            for (int i = 0; i < numThreadGroups; i++) {
                groups[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // When all the threads from all thread groups have completed, take an endTime timestamp
        long endTime = System.currentTimeMillis();

        double wallTime = (endTime - startTime) / 1000d; // Convert to seconds
        double throughput = (2000.0 * threadGroupSize * numThreadGroups) / wallTime;

        System.out.println("Wall Time: " + wallTime + " seconds");
        System.out.println("Throughput: " + throughput + " requests/second");

    }

}
