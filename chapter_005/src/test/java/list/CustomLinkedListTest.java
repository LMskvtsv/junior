package list;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CustomLinkedListTest {

    CustomLinkedList<Integer> list;

    @Before
    public void setUp() {
        list = new CustomLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
    }

    @Test
    public void whenAddFiveElementsAndGetOneThenTwo() {
        assertThat(list.get(1), is(2));
    }

    @Test
    public void whenAddFiveElementsAndGetFourThenFive() {
        assertThat(list.get(4), is(5));
    }

    @Test
    public void whenIterateThanAllElementsExist() {
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(5);
        expected.add(4);
        expected.add(3);
        expected.add(2);
        expected.add(1);
        int counter = 0;
        for (int i : list) {
            assertThat(i, is(expected.get(counter)));
            counter++;
        }
        assertThat(counter, is(5));
    }
}