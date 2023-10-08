package client2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class WriteCSV {

    public static void writeLogsToCSV(List<RequestLog> logs) {
        try (FileWriter fw = new FileWriter("logs.csv", true);  // true means append, set to false if you want to overwrite the file each time
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {
            for (RequestLog log : logs) {
                out.println(log.getStartTime() + "," + log.getRequestType() + "," + log.getLatency()
                    + "," + log.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
