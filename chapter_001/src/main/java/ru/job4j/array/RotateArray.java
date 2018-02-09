package ru.job4j.array;


/**
 * Class for rotate array task.
 */
public class RotateArray {
    /**
     * Rotates array (clockwise).
     *
     * @param array - initial array
     * @return int[][] - array after rotating.
     */
    public int[][] sort(int[][] array) {
        for (int i = 0; i < array.length / 2; i++) {
            for (int j = 0; j < ((array.length % 2 == 0) ? (array.length / 2) : (array.length / 2 + 1)); j++) {
                int tmp1 = array[j][i];
                array[j][i] = array[array.length - 1 - i][j];
                array[array.length - 1 - i][j] = array[array.length - 1 - j][array.length - 1 - i];
                array[array.length - 1 - j][array.length - 1 - i] = array[i][array.length - 1 - j];
                array[i][array.length - 1 - j] = tmp1;
            }
        }
        return array;
    }
}
