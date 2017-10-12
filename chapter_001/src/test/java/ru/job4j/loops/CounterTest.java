package ru.job4j.loops;

import org.junit.*;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

/**
 * Class for Counter tests.
 */
public class CounterTest {

    /**
     * Check if will be added even numbers only.
     */
    @Test
    public void counterTest() {
        Counter counter = new Counter();
        int actual = counter.add(0, 10);
        int expected = 20;
        assertThat(actual, is(expected));
    }
}
