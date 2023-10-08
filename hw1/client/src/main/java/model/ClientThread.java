package model;

import static model.CallAPIs.getAPI;
import static model.CallAPIs.postAPI;

import okhttp3.OkHttpClient;

public class ClientThread implements Runnable {

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
            postAPI(IPAddr, client);
            getAPI(IPAddr, client);
        }
    }

}
