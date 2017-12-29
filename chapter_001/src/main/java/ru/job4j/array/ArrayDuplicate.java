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
        int numberOfDuplicates = 0;
        int lastIndex = array.length - 1;
        for (int i = 0; i < lastIndex - numberOfDuplicates; i++) {
            for (int j = i; j < lastIndex - numberOfDuplicates; j++) {
                if (array[i].equals(array[j + 1])) {
                    String tmp = array[lastIndex - numberOfDuplicates];
                    array[lastIndex - numberOfDuplicates] = array[j + 1];
                    array[j + 1] = tmp;
                    numberOfDuplicates++;
                    if (array[i].equals(tmp)) {
                        j--;
                    }
                }
            }
        }
        return Arrays.copyOf(array, array.length - numberOfDuplicates);
    }
}
