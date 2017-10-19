package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for BubbleSort test.
 */
public class BubbleSortTest {
    /**
     * Check if array will be sorted.
     */
    @Test
    public void sort() {
        int[] a = new int[]{5, 4, 7, 1};
        BubbleSort bs = new BubbleSort();
        int[] actual = bs.sort(a);
        int[] expected = new int[]{1, 4, 5, 7};
        assertThat(actual, is(expected));
    }
}