package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for Turn test.
 */
public class TurnTest {
    /**
     * Check if array will be inverted. Even number of elements.
     */
    @Test
    public void evenNumberOfElements() {
        int[] a = new int[]{6, 3, 7, 1};
        Turn t = new Turn();
        int[] actual = t.back(a);
        int[] expected = new int[]{1, 7, 3, 6};
        assertThat(actual, is(expected));
    }

    /**
     * Check if array will be inverted. Uneven number of elements.
     */
    @Test
    public void unevenHeight() {
        int[] a = new int[]{6, 3, 7, 1, 8};
        Turn t = new Turn();
        int[] actual = t.back(a);
        int[] expected = new int[]{8, 1, 7, 3, 6};
        assertThat(actual, is(expected));
    }
}