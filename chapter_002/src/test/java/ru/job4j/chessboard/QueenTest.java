package ru.job4j.chessboard;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QueenTest {

    @Test
    public void possibleMovesMixedDirections() {
        Queen test = new Queen(new Cell(4, 4));
        Cell[] expectedMoves = new Cell[]{
                new Cell(5, 5),
                new Cell(6, 6),
                new Cell(7, 7),
                new Cell(8, 8),
                new Cell(5, 3),
                new Cell(6, 2),
                new Cell(7, 1),
                new Cell(3, 5),
                new Cell(2, 6),
                new Cell(1, 7),
                new Cell(3, 3),
                new Cell(2, 2),
                new Cell(1, 1),

                new Cell(5, 4),
                new Cell(6, 4),
                new Cell(7, 4),
                new Cell(8, 4),
                new Cell(4, 3),
                new Cell(4, 2),
                new Cell(4, 1),
                new Cell(3, 4),
                new Cell(2, 4),
                new Cell(1, 4),
                new Cell(4, 5),
                new Cell(4, 6),
                new Cell(4, 7),
                new Cell(4, 8),
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test(expected = ImpossibleMoveException.class)
    public void wayImpossibleMove() {
        Queen test = new Queen(new Cell(4, 4));
        test.way(new Cell(3, 2));
    }

    @Test
    public void wayShortest() {
        Queen test = new Queen(new Cell(3, 5));
        Cell[] expectedWay = new Cell[]{
                new Cell(3, 6)
        };
        Cell[] actualWay = test.way(new Cell(3, 6));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }
}
