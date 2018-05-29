package set;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleSetTest {

    @Test
    public void whenAddDifferentElementsThenAllAdded() {
        SimpleSet<Integer> set = new SimpleSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        assertThat(set.contains(1), is(true));
        assertThat(set.contains(2), is(true));
        assertThat(set.contains(3), is(true));
        assertThat(set.getSize(), is(3));
    }

    @Test
    public void whenAddSameElementThenAddedOne() {
        SimpleSet<Integer> set = new SimpleSet<>();
        set.add(1);
        set.add(1);
        assertThat(set.contains(1), is(true));
        assertThat(set.getSize(), is(1));
    }

}