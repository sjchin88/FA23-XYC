package client2;

import static client2.CallAPIs.getAPI;
import static client2.CallAPIs.postAPI;

import java.util.*;
import okhttp3.OkHttpClient;

public class ClientThread implements Runnable {

    private List<RequestLog> localLogs = new ArrayList<>();
    public static List<RequestLog> requestLogs = Collections.synchronizedList(new ArrayList<>());

    private int numOfCalls;
    private String IPAddr;

    public ClientThread(int numOfCalls, String IPAddr) {
        this.numOfCalls = numOfCalls;
        this.IPAddr = IPAddr;
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();
        for (int i = 0; i < numOfCalls; i++) {
            call(client);
        }
        requestLogs.addAll(localLogs);
    }

    private void call(OkHttpClient client) {
        long postStart = System.currentTimeMillis();
        int postCode = postAPI(IPAddr, client);
        long postEnd = System.currentTimeMillis();
        localLogs.add(new RequestLog(postStart, "POST", postEnd - postStart, postCode));
        long getStart = System.currentTimeMillis();
        int getCode = getAPI(IPAddr, client);
        long getEnd = System.currentTimeMillis();
        localLogs.add(new RequestLog(getStart, "GET", getEnd - getStart, getCode));
    }

}
