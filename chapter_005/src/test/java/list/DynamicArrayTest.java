package list;

import org.junit.Test;

import java.util.ConcurrentModificationException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DynamicArrayTest {

    @Test
    public void whenAddSevenElementsThenSizeSeven() {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        dynamicArray.add(11);
        dynamicArray.add(12);
        dynamicArray.add(13);
        dynamicArray.add(14);
        assertThat(dynamicArray.get(3), is(14));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenSizeThreeAddThreeElementsAddOneElementWhileIterateThenException() {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        dynamicArray.add(11);
        dynamicArray.add(12);
        dynamicArray.add(13);
        for (int i : dynamicArray) {
            dynamicArray.add(14);
        }
    }
}