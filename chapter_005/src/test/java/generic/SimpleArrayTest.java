package generic;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SimpleArrayTest {

    @Test
    public void whenAddStringThanItExists() {
        SimpleArray<String> simpleArray = new SimpleArray<>(5);
        simpleArray.add("first element");
        assertThat(simpleArray.getValue(0), is("first element"));
    }

    @Test
    public void whenAddIntThanItExists() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(5);
        simpleArray.add(1);
        assertThat(simpleArray.getValue(0), is(1));
    }

    @Test
    public void whenSetStringThanItIsChanged() {
        SimpleArray<String> simpleArray = new SimpleArray<>(5);
        simpleArray.add("first element");
        simpleArray.add("second element");
        simpleArray.set(0, "changed element");
        assertThat(simpleArray.getValue(0), is("changed element"));
    }

    @Test
    public void whenDeleteStringThanItIsDeleted() {
        SimpleArray<String> simpleArray = new SimpleArray<>(5);
        simpleArray.add("first element");
        simpleArray.add("second element");
        simpleArray.delete(0);
        assertThat(simpleArray.getValue(0), is("second element"));
    }

    @Test
    public void whenForEachThenIterate() {
        SimpleArray<String> simpleArray = new SimpleArray<>(5);
        simpleArray.add("first element");
        simpleArray.add("second element");
        simpleArray.add("third element");
        int counter = 0;
        for (String s : simpleArray) {
            counter++;
        }
        assertThat(counter, is(5));
    }
}