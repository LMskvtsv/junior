package set;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleLinkedSetTest {

    @Test
    public void whenAddedSeveralDifferentElementsThanAllExist() {
        SimpleLinkedSet<Integer> list = new SimpleLinkedSet<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertThat(list.contains(1), is(true));
        assertThat(list.contains(2), is(true));
        assertThat(list.contains(3), is(true));
        assertThat(list.getSize(), is(3));
    }

    @Test
    public void whenAddedSeveralSameElementsThanOneExists() {
        SimpleLinkedSet<Integer> list = new SimpleLinkedSet<>();
        list.add(1);
        list.add(1);
        assertThat(list.contains(1), is(true));
        assertThat(list.getSize(), is(1));
    }

    @Test
    public void iteratorNotEmptyCollection() {
        SimpleLinkedSet<Integer> list = new SimpleLinkedSet<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Iterator iterator = list.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(3));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorEmptyCollection() {
        SimpleLinkedSet<Integer> list = new SimpleLinkedSet<>();
        Iterator iterator = list.iterator();
        assertThat(iterator.hasNext(), is(false));
        iterator.next();
    }
}