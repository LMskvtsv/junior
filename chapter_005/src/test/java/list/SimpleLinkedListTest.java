package list;

import org.junit.Test;
import org.junit.Before;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class SimpleLinkedListTest {

    private SimpleLinkedList<Integer> list;

    @Before
    public void beforeTest() {
        list = new SimpleLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
    }

    @Test
    public void whenAddThreeElementsThenUseGetOneResultTwo() {
        assertThat(list.get(1), is(4));
    }

    @Test
    public void whenAddThreeElementsThenUseGetSizeResultThree() {
        assertThat(list.getSize(), is(5));
    }

    @Test
    public void whenDeleteFirstElementThenUseGetZeroResultTwo() {
        list.deleteFirstAddedElement();
        assertThat(list.get(list.getSize() - 1), is(2));
    }

    @Test
    public void whenDeleteLastElementThenUseGetLastResultTwo() {
        assertThat(list.get(0), is(5));
        list.deleteLastAddedElement();
        assertThat(list.get(0), is(4));
    }
}