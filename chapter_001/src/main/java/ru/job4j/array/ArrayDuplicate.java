package ru.job4j.array;

import java.util.Arrays;

/**
 * Class for removing duplicates in array task.
 */
public class ArrayDuplicate {
    /**
     * Removes array duplicates.
     *
     * @param array - initial array
     * @return String[] - array without duplicates.
     */
    public String[] removeDuplicates(String[] array) {
        int duplicates = 0;
        int last = array.length - 1;
        for (int i = 0; i < last - duplicates; i++) {
            for (int j = i; j < last - duplicates; j++) {
                if (array[i].equals(array[j + 1])) {
                    String tmp = array[last - duplicates];
                    array[last - duplicates] = array[j + 1];
                    array[j + 1] = tmp;
                    duplicates++;
                    if (array[i].equals(tmp)) {
                        j--;
                    }
                }
            }
        }
        return Arrays.copyOf(array, array.length - duplicates);
    }
}
