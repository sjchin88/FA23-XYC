import model.ClientThread;

public class Main {

    public static void main(String[] args) {
        test(1, 1, 0,"http://54.149.200.68:3000");
//        test(10, 10, 2,"http://54.149.200.68:3000");
    }

    public static void test(int threadGroupSize, int numThreadGroups, long delay, String IPAddr) {
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

        for (int i = 0; i < numThreadGroups; i++) {
            Thread[] currGroup = new Thread[threadGroupSize];
            for (int j = 0; j < threadGroupSize; j++) {
                currGroup[j] = new Thread(new ClientThread(1000, IPAddr));
                currGroup[j].start();
            }
            try {
                for (int j = 0; j < threadGroupSize; j++) {
                    currGroup[j].join();
                }
                Thread.sleep(delay*1000);
            } catch (InterruptedException e) {
            }
        }

        // When all the threads from all thread groups have completed, take an endTime timestamp
        long endTime = System.currentTimeMillis();

        double wallTime = (endTime - startTime) / 1000d; // Convert to seconds
        double throughput = (2000.0 * threadGroupSize * numThreadGroups) / wallTime;

        System.out.println("Wall Time: " + wallTime + " seconds");
        System.out.println("Throughput: " + throughput + " requests/second");

    }

}
