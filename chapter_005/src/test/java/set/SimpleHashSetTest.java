package set;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleHashSetTest {
    @Test
    public void whenAddDifferentElementsThenAllAdded() {
        SimpleHashSet<Integer> simpleHashSet = new SimpleHashSet<>();
        simpleHashSet.add(1);
        simpleHashSet.add(2);
        simpleHashSet.add(3);
        assertThat(simpleHashSet.contains(1), is(true));
        assertThat(simpleHashSet.contains(2), is(true));
        assertThat(simpleHashSet.contains(3), is(true));
        assertThat(simpleHashSet.contains(4), is(false));
        assertThat(simpleHashSet.getSize(), is(3));
    }

    @Test
    public void whenAddSameElementsThenOneAdded() {
        SimpleHashSet<Integer> simpleHashSet = new SimpleHashSet<>();
        simpleHashSet.add(1);
        simpleHashSet.add(1);
        assertThat(simpleHashSet.contains(1), is(true));
        assertThat(simpleHashSet.getSize(), is(1));
    }

    @Test
    public void whenElementWasRemovedThanContainsFalseAndSizeLess() {
        SimpleHashSet<Integer> simpleHashSet = new SimpleHashSet<>();
        simpleHashSet.add(1);
        assertThat(simpleHashSet.contains(1), is(true));
        assertThat(simpleHashSet.getSize(), is(1));
        simpleHashSet.remove(1);
        assertThat(simpleHashSet.contains(1), is(false));
        assertThat(simpleHashSet.getSize(), is(0));
    }
}