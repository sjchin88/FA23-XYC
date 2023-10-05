package week2.bsdsthreads;

/**
 * A trivial thread that stores and prints a string
 */
public class NamingThread implements Runnable {

    private String name;

    // default constructor
    public NamingThread() {
    }

    public NamingThread(String threadName) {
        this.name = threadName;
        System.out.println("Constructor called: " + threadName) ;
    }

    public void setName (String threadName) {
        name = threadName;
    }

    public void run() {
        //Display info about this  thread
        System.out.println("Run called : " + name);
        System.out.println(name + " : " + Thread.currentThread());
        // and terminate  ....
    }
}
