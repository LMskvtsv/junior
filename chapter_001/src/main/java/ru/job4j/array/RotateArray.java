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

    /**
     * Rotates array (clockwise).
     *
     * @param args - initial array
     */
    public static void main(String[] args) {
        int[][] a = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                System.out.print(a[i][j] + " ");

            }
            System.out.println();
        }
        System.out.println("--------------------------");
        RotateArray ra = new RotateArray();
        int[][] b = ra.sort(a);

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                System.out.print(b[i][j] + " ");

            }
            System.out.println();
        }
    }
}
