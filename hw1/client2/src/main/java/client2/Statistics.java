package client2;

import java.util.ArrayList;
import java.util.List;

public class Statistics {
    public static void printStats(List<RequestLog> logs) {
        List<Long> postLatencies = new ArrayList<>();
        List<Long> getLatencies = new ArrayList<>();

        for(RequestLog log : logs) {
            if(log.getRequestType().equals("POST")) {
                postLatencies.add(log.getLatency());
            } else if(log.getRequestType().equals("GET")) {
                getLatencies.add(log.getLatency());
            }
        }

        System.out.println("POST STATS:");
        printStat(postLatencies);

        System.out.println("GET STATS:");
        printStat(getLatencies);
    }
    private static void printStat(List<Long> latencies) {
        latencies.sort(Long::compareTo);
        double mean = latencies.stream().mapToLong(val -> val).average().orElse(0.0);
        double median = latencies.get(latencies.size()/2);
        double p99 = latencies.get((int) Math.ceil(0.99 * latencies.size()) - 1);
        double min = latencies.get(0);
        double max = latencies.get(latencies.size() - 1);

        System.out.println("Mean Response Time: " + mean);
        System.out.println("Median Response Time: " + median);
        System.out.println("99th Percentile Response Time: " + p99);
        System.out.println("Min Response Time: " + min);
        System.out.println("Max Response Time: " + max);
    }
}
