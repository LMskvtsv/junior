package ru.job4j.loops;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for Paint test.
 */
public class PaintTest {
    /**
     * Check if the pyramid will have 3 lines.
     */
    @Test
    public void threeLines() {
        Paint paint = new Paint();
        String actual = paint.pyramid(3);
        StringBuilder builder = new StringBuilder();
        builder.append("  ^  " + System.getProperty("line.separator"));
        builder.append(" ^^^ " + System.getProperty("line.separator"));
        builder.append("^^^^^" + System.getProperty("line.separator"));
        String expected = builder.toString();
        assertThat(actual, is(expected));
    }

    /**
     * Check if the pyramid will have 2 lines.
     */
    @Test
    public void twoLines() {
        Paint paint = new Paint();
        String actual = paint.pyramid(2);
        StringBuilder builder = new StringBuilder();
        builder.append(" ^ " + System.getProperty("line.separator"));
        builder.append("^^^" + System.getProperty("line.separator"));
        String expected = builder.toString();
        assertThat(actual, is(expected));
    }
}