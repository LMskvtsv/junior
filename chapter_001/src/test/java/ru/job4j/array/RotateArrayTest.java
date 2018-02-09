package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for RotateArray test.
 */
public class RotateArrayTest {
    /**
     * Check if array will be rotated 2x2.
     */
    @Test
    public void twoXTwo() {
        int[][] array = new int[][]{{1, 2}, {3, 4}};
        RotateArray ra = new RotateArray();
        int[][] actual = ra.sort(array);
        int[][] expected = new int[][]{{3, 1}, {4, 2}};
        assertThat(actual, is(expected));
    }

    /**
     * Check if array will be rotated 2x2.
     */
    @Test
    public void threeXThree() {
        int[][] array = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RotateArray ra = new RotateArray();
        int[][] actual = ra.sort(array);
        int[][] expected = new int[][]{{7, 4, 1}, {8, 5, 2}, {9, 6, 3}};
        assertThat(actual, is(expected));
    }
}