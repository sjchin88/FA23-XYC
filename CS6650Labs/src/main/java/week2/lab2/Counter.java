package week2.lab2;

public class Counter {
    private long count = 0;

    public synchronized void increment() {
        count += 1;
    }

    public synchronized long getCount() {
        return count;
    }
}
