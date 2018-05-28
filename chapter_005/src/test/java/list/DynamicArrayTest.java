package list;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

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

    @Test
    public void whenHasNextThanHasNextTrue() {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        dynamicArray.add(11);
        assertThat(dynamicArray.iterator().hasNext(), is(true));
    }

    @Test
    public void whenHasntNextThanHasNextFalse() {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        dynamicArray.add(11);
        dynamicArray.add(12);
        dynamicArray.add(13);
        Iterator i = dynamicArray.iterator();
        i.next();
        i.next();
        i.next();
        assertThat(i.hasNext(), is(false));
    }

    @Test
    public void whenMultipleHasNextThanNextIsFirstElement() {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        dynamicArray.add(11);
        dynamicArray.add(12);
        dynamicArray.add(13);
        Iterator i = dynamicArray.iterator();
        i.hasNext();
        i.hasNext();
        i.hasNext();
        assertThat(i.next(), is(11));
    }

    @Test(expected = NoSuchElementException.class)
    public void checkEveryElement() {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        dynamicArray.add(11);
        dynamicArray.add(12);
        dynamicArray.add(13);
        Iterator i = dynamicArray.iterator();
        assertThat(i.next(), is(11));
        assertThat(i.next(), is(12));
        assertThat(i.next(), is(13));
        i.next();
    }
}