package model;

import static model.CallAPIs.getAPI;
import static model.CallAPIs.postAPI;

public class ClientThread implements Runnable {

    private int numOfCalls;
    private String IPAddr;

    public ClientThread(int numOfCalls, String IPAddr) {
        this.numOfCalls = numOfCalls;
        this.IPAddr = IPAddr;
    }

    @Override
    public void run() {
        for (int i = 0; i < numOfCalls; i++) {
            postAPI(IPAddr);
            getAPI(IPAddr);
        }
    }

}
