package ru.job4j.array;

import java.util.HashSet;

public class ArraySumOfTwo {

    public boolean sumOfTwo(int[] array, int n) {
        HashSet<Integer> set = new HashSet<>();
        boolean result = false;
        for (int i: array) {
            int diff = n - i;
            if (!set.contains(diff)) {
                set.add(i);
            } else {
                result = true;
                break;
            }
        }
        return result;
    }
}
