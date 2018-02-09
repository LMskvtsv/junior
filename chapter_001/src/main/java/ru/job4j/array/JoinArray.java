package ru.job4j.array;

/**
 * Class for joining task.
 */
public class JoinArray {

    /**
     * Method joins two sorted (in ascending order) arrays in one sorted (in ascending order) array.
     *
     * @param array1 - sorted array 1
     * @param array2 - sorted array 2
     * @return int[] - array after joining.
     */
    public int[] join(int[] array1, int[] array2) {
        int[] joinedArray = new int[array1.length + array2.length];
        int first = 0;
        int second = 0;
        int joined = 0;
        for (int i = 0; i < joinedArray.length; i++) {
            if (first < array1.length && second < array2.length) {
                joinedArray[i] =
                        (array1[first] < array2[second])
                                ? array1[first++]
                                : array2[second++];
                joined++;
            }
        }
        if (second == array2.length && first < array1.length) {
            System.arraycopy(array1, first, joinedArray, joined, array1.length - first);
        } else if (first == array1.length && second < array2.length) {
            System.arraycopy(array2, second, joinedArray, joined, array2.length - second);
        }
        return joinedArray;
    }
}

