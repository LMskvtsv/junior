package list;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;


public class SimpleQueueTest {

    @Test
    public void whenFirstInThenFirstOut() {
        SimpleQueue<Integer> simpleQueue = new SimpleQueue<>();
        simpleQueue.push(11);
        simpleQueue.push(12);
        simpleQueue.push(13);
        simpleQueue.push(14);
        assertThat(simpleQueue.poll(), is(11));
        assertThat(simpleQueue.poll(), is(12));
        assertThat(simpleQueue.poll(), is(13));
        assertThat(simpleQueue.poll(), is(14));
    }

    @Test
    public void whenEmptyStackThenNull() {
        SimpleStack<Integer> simpleStack = new SimpleStack<>();
        assertNull(simpleStack.poll());
    }
}