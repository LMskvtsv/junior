package search;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PriorityQueueTest {
    @Test
    public void whenHigherPriority() {
        PriorityQueue queue = new PriorityQueue();
        queue.put(new Task("low", 5));
        queue.put(new Task("urgent1", 1));
        queue.put(new Task("urgent2", 1));
        queue.put(new Task("middle", 3));
        queue.put(new Task("trivial", 6));
        Task result = queue.take();
        assertThat(result.getDesc(), is("urgent2"));
        result = queue.take();
        assertThat(result.getDesc(), is("urgent1"));
        result = queue.take();
        assertThat(result.getDesc(), is("middle"));
        result = queue.take();
        assertThat(result.getDesc(), is("low"));
        result = queue.take();
        assertThat(result.getDesc(), is("trivial"));
    }
}
