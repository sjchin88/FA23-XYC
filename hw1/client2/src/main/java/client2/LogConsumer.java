package client2;

import java.util.concurrent.BlockingQueue;

public class LogConsumer implements Runnable {
    private final BlockingQueue<RequestLog> logs;
    public LogConsumer(BlockingQueue<RequestLog> logs) {
        this.logs = logs;
    }

    @Override
    public void run() {
        try {
            while (true) {
                RequestLog log = logs.take();
                if (log == RequestLog.POISON_PILL) break; // Terminate on sentinel value
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
