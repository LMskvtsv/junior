package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 * Class for ArrayDuplicate test.
 */
public class ArrayDuplicateTest {
    /**
     * Check if nothing will happened in case there is no duplicates in array.
     */
    @Test
    public void thereIsNoDuplicates() {
        String[] array = new String[]{"Привет", "Огонь", "Мир", "Супер"};
        ArrayDuplicate ra = new ArrayDuplicate();
        String[] actual = ra.removeDuplicates(array);
        String[] expected = new String[]{"Привет", "Огонь", "Мир", "Супер"};
        assertThat(actual, arrayContainingInAnyOrder(expected));
    }

    /**
     * Check if duplicates will be removed.
     */
    @Test
    public void thereIsDuplicatesTwoFirst() {
        String[] array = new String[]{"Привет", "Привет", "Мир", "Супер", "Огонь"};
        ArrayDuplicate ra = new ArrayDuplicate();
        String[] actual = ra.removeDuplicates(array);
        String[] expected = new String[]{"Привет", "Мир", "Супер", "Огонь"};
        assertThat(actual, arrayContainingInAnyOrder(expected));
    }

    /**
     * Check if duplicates will be removed.
     */
    @Test
    public void thereIsDuplicatesFirstSecondAndLast() {
        String[] array = new String[]{"Привет", "Привет", "Мир", "Супер", "Привет"};
        ArrayDuplicate ra = new ArrayDuplicate();
        String[] actual = ra.removeDuplicates(array);
        String[] expected = new String[]{"Привет", "Мир", "Супер"};
        assertThat(actual, arrayContainingInAnyOrder(expected));
    }

    /**
     * Check if duplicates will be removed.
     */
    @Test
    public void thereIsDuplicatesTwoLast() {
        String[] array = new String[]{"Привет", "Супер", "Мир", "Огонь", "Огонь"};
        ArrayDuplicate ra = new ArrayDuplicate();
        String[] actual = ra.removeDuplicates(array);
        String[] expected = new String[]{"Привет", "Супер", "Мир", "Огонь"};
        assertThat(actual, arrayContainingInAnyOrder(expected));
    }

    /**
     * Check if duplicates will be removed.
     */
    @Test
    public void allElementsTheSame() {
        String[] array = new String[]{"Привет", "Привет", "Привет", "Привет", "Привет"};
        ArrayDuplicate ra = new ArrayDuplicate();
        String[] actual = ra.removeDuplicates(array);
        String[] expected = new String[]{"Привет"};
        assertThat(actual, arrayContainingInAnyOrder(expected));
    }
}