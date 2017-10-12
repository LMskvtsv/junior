package ru.job4j.loops;

/**
 * Factorial task.
 */
public class Factorial {
    /**
     * Calculate factorial.
     *
     * @param n - number
     * @return int - factorial of the number. Factorial 0 = 1,
     * factorial of negative number doesn't exest - method returns 0.
     * max n is 12 because of int size. In case n > 12 method returns 0.
     */
    public int calc(int n) {

        if (n == 0) {
            return 1;
        } else if (n < 0 || n > 12) {
            return 0;
        } else {
            return calc(n - 1) * n;
        }

    }
}

