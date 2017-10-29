package ru.job4j.array;

/**
 * Class for sorting task.
 */
public class BubbleSort {
    /**
     * Sorts array (ascending).
     *
     * @param array - initial array
     * @return int[] - array after sorting.
     */
    public int[] sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            boolean isChanged = false;
            for (int j = 0; j < array.length - 1; j++) {
                int tmp;
                if (array[j] > array[j + 1]) {
                    tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    isChanged = true;
                }
            }
            if (!isChanged) {
                break;
            }
        }
        return array;
    }
}
