package model;

import static model.CallAPIs.getAPI;
import static model.CallAPIs.postAPI;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;

public class ClientThread implements Runnable {

    private int numOfCalls;
    private String IPAddr;

    public ClientThread(int numOfCalls, String IPAddr) {
        this.numOfCalls = numOfCalls;
        this.IPAddr = IPAddr;
    }

    @Override
    public void run() {
        HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
        for (int i = 0; i < numOfCalls; i++) {
            postAPI(IPAddr, client);
            getAPI(IPAddr, client);
        }
    }

}
