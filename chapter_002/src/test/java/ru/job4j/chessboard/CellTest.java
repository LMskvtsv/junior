package ru.job4j.chessboard;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class CellTest {

    @Test
    public void whenCellCorrect() {
        Cell test = new Cell(3, 5);
        assertThat(test.getX(), is(3));
        assertThat(test.getY(), is(5));
    }

    @Test(expected = ImpossibleCellException.class)
    public void whenCellIncorrect() {
        new Cell(9, 0);
    }
}
