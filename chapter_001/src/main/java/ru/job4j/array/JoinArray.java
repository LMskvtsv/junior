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
        int length1 = array1.length;
        int length2 = array2.length;
        int newArrayLength = length1 + length2;
        int[] joinedArray = new int[newArrayLength];
        int index = 0;
        if (length1 < length2) {
            for (int i = 0; i < length1; i++) {
                if (array1[i] <= array2[i]) {
                    joinedArray[index] = array1[i];
                    joinedArray[index + 1] = array2[i];
                } else {
                    joinedArray[index] = array2[i];
                    joinedArray[index + 1] = array1[i];
                }
                index = index + 2;
            }
            for (int i = index; i < newArrayLength; i++) {
                joinedArray[i] = array2[i - length1];
            }
        } else {
            for (int i = 0; i < length2; i++) {
                if (array2[i] <= array1[i]) {
                    joinedArray[index] = array2[i];
                    joinedArray[index + 1] = array1[i];
                } else {
                    joinedArray[index] = array1[i];
                    joinedArray[index + 1] = array2[i];
                }
                index = index + 2;
            }
            for (int i = index; i < newArrayLength; i++) {
                joinedArray[i] = array1[i - length2];
            }
        }
        return joinedArray;
    }
}
