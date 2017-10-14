package ru.job4j.loops;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for Board test.
 */
public class BoardTest {
    /**
     * Check if the string will be like chess board in case even height.
     */
    @Test
    public void evenHeight() {
        Board board = new Board();
        String actual = board.paintWithHint(5, 4);
        StringBuilder builder = new StringBuilder();
        builder.append("X X X" + System.getProperty("line.separator"));
        builder.append(" X X " + System.getProperty("line.separator"));
        builder.append("X X X" + System.getProperty("line.separator"));
        builder.append(" X X " + System.getProperty("line.separator"));
        String expected = builder.toString();
        assertThat(actual, is(expected));
    }

    /**
     * Check if the string will be like chess board in case uneven height.
     */
    @Test
    public void unevenHeight() {
        Board board = new Board();
        String actual = board.paintWithHint(7, 5);
        StringBuilder builder = new StringBuilder();
        builder.append("X X X X" + System.getProperty("line.separator"));
        builder.append(" X X X " + System.getProperty("line.separator"));
        builder.append("X X X X" + System.getProperty("line.separator"));
        builder.append(" X X X " + System.getProperty("line.separator"));
        builder.append("X X X X" + System.getProperty("line.separator"));
        String expected = builder.toString();
        assertThat(actual, is(expected));
    }

    /**
     * Check if board will no be painted in case width is even.
     */
    @Test
    public void evenWidth() {
        Board board = new Board();
        String actual = board.paintWithHint(4, 5);
        String expected = "Width should be uneven!";
        assertThat(actual, is(expected));
    }
}