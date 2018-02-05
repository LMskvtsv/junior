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
        int firstCounter = 0;
        int secondCounter = 0;
        for (int i = 0; i < joinedArray.length; i++) {
            if (firstCounter < array1.length && secondCounter < array2.length) {
                joinedArray[i] =
                        (array1[firstCounter] < array2[secondCounter]) ?
                        array1[firstCounter++] :
                        array2[secondCounter++];
            } else if (secondCounter == array2.length && firstCounter < array1.length) {
                joinedArray[i] = array1[firstCounter++];
            } else if (firstCounter == array1.length && secondCounter < array2.length) {
                joinedArray[i] = array2[secondCounter++];
            }
        }
        return joinedArray;
    }
}
