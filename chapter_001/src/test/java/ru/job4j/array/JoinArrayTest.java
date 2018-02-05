package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Class for JoinArray test.
 */
public class JoinArrayTest {
    /**
     * Check join in case first array larger then second.
     */
    @Test
    public void join() {
        int[] array1 = new int[]{1, 4, 6, 9};
        int[] array2 = new int[]{1, 3, 7};
        JoinArray joinArray = new JoinArray();
        int[] actual = joinArray.join(array1, array2);
        int[] expected = new int[]{1, 1, 3, 4, 6, 7, 9};
        assertThat(actual, is(expected));
    }

    /**
     * Check join in case first array has same length as second.
     */

    @Test
    public void join1() {
        int[] array1 = new int[]{1, 4, 6};
        int[] array2 = new int[]{1, 3, 7};
        JoinArray joinArray = new JoinArray();
        int[] actual = joinArray.join(array1, array2);
        int[] expected = new int[]{1, 1, 3, 4, 6, 7};
        assertThat(actual, is(expected));
    }

    /**
     * Check join in case second array larger then first.
     */

    @Test
    public void join2() {
        int[] array1 = new int[]{1, 3, 7};
        int[] array2 = new int[]{1, 4, 6, 9, 10, 12, 13};
        JoinArray joinArray = new JoinArray();
        int[] actual = joinArray.join(array1, array2);
        int[] expected = new int[]{1, 1, 3, 4, 6, 7, 9, 10, 12, 13};
        assertThat(actual, is(expected));
    }

    /**
     * Check join in case all elements the same.
     */

    @Test
    public void join3() {
        int[] array1 = new int[]{1, 2, 3};
        int[] array2 = new int[]{1, 2, 3};
        JoinArray joinArray = new JoinArray();
        int[] actual = joinArray.join(array1, array2);
        int[] expected = new int[]{1, 1, 2, 2, 3, 3};
        assertThat(actual, is(expected));
    }

    /**
     * Check join when indexes are not the same.
     */

    @Test
    public void join4() {
        int[] array1 = new int[]{2, 4, 5, 6, 9};
        int[] array2 = new int[]{1, 3, 8, 10};
        JoinArray joinArray = new JoinArray();
        int[] actual = joinArray.join(array1, array2);
        int[] expected = new int[]{1, 2, 3, 4, 5, 6, 8, 9, 10};
        assertThat(actual, is(expected));
    }
}