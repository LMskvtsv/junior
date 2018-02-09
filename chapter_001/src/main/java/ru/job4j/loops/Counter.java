package ru.job4j.loops;

/**
 * Class for loop task.
 */
public class Counter {

    /**
     * Method calculates sum of even numbers from start to finish.
     *
     * @param start  - first number
     * @param finish - last number
     * @return int  - sum of even numbers from first to last.
     */
    public int add(int start, int finish) {
        int sum = 0;
        for (int i = start; i < finish; i++) {
            if (i % 2 == 0) {
                sum = sum + i;
            }
        }
        return sum;
    }
}
