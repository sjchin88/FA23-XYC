package week2.lab2;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Time taken by Vector: 10 ms
 * Time taken by ArrayList: 4 ms
 */

public class CollectionTest1 {

    private static final int ELEMENTS = 100_000;

    public static void main(String[] args) {
        // Testing with Vector
        Vector<Integer> vector = new Vector<>();
        long startTimeVector = System.currentTimeMillis();
        for (int i = 0; i < ELEMENTS; i++) {
            vector.add(i);
        }
        long endTimeVector = System.currentTimeMillis();

        // Testing with ArrayList
        ArrayList<Integer> arrayList = new ArrayList<>();
        long startTimeArrayList = System.currentTimeMillis();
        for (int i = 0; i < ELEMENTS; i++) {
            arrayList.add(i);
        }
        long endTimeArrayList = System.currentTimeMillis();

        // Print the results
        System.out.println("Time taken by Vector: " + (endTimeVector - startTimeVector) + " ms");
        System.out.println(
            "Time taken by ArrayList: " + (endTimeArrayList - startTimeArrayList) + " ms");
    }
}
