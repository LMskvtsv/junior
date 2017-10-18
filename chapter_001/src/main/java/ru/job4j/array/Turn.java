package ru.job4j.array;

/**
 * Class for turning array back.
 */
public class Turn {
    /**
     * Turns array back to front.
     *
     * @param array - initial array
     * @return int[] - array after turning backwards.
     */
    public int[] back(int[] array) {
        int tmp;
        for (int i = 0; i < array.length / 2; i++) {
            tmp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = tmp;
        }
        return array;
    }
}
