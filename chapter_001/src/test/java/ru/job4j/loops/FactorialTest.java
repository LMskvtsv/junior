package ru.job4j.loops;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for Factorial tests.
 */
public class FactorialTest {
    /**
     * Check factorial of positive number.
     */
    @Test
    public void counterTestPositive() {
        Factorial fact = new Factorial();
        int actual = fact.calc(10);
        int expected = 3_628_800;
        assertThat(actual, is(expected));
    }

    /**
     * Check factorial of zero.
     */
    @Test
    public void counterTestZero() {
        Factorial fact = new Factorial();
        int actual = fact.calc(0);
        int expected = 1;
        assertThat(actual, is(expected));
    }

    /**
     * Check factorial of negative number.
     */
    @Test
    public void counterTestNegative() {
        Factorial fact = new Factorial();
        int actual = fact.calc(-1);
        int expected = 0;
        assertThat(actual, is(expected));
    }

    /**
     * Check factorial of number, which greater then 12.
     */
    @Test
    public void counterTestMoreThen12() {
        Factorial fact = new Factorial();
        int actual = fact.calc(13);
        int expected = 0;
        assertThat(actual, is(expected));
    }

}

