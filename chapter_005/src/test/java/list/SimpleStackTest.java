package list;

import org.junit.Test;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class SimpleStackTest {

    @Test
    public void whenLastInThenFirstOut() {
        SimpleStack<Integer> simpleStack = new SimpleStack<>();
        simpleStack.push(11);
        simpleStack.push(12);
        simpleStack.push(13);
        simpleStack.push(14);
        assertThat(simpleStack.poll(), is(14));
        assertThat(simpleStack.poll(), is(13));
        assertThat(simpleStack.poll(), is(12));
        assertThat(simpleStack.poll(), is(11));

    }

    @Test
    public void whenEmptyStackThenNull() {
        SimpleStack<Integer> simpleStack = new SimpleStack<>();
        assertNull(simpleStack.poll());
    }

}