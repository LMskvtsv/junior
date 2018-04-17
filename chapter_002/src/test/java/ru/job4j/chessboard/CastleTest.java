package ru.job4j.chessboard;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CastleTest {

    @Test
    public void possibleMovesUpAndRight() {
        Castle test = new Castle(new Cell(1, 1));
        Cell[] expectedMoves = new Cell[]{
                new Cell(2, 1),
                new Cell(3, 1),
                new Cell(4, 1),
                new Cell(5, 1),
                new Cell(6, 1),
                new Cell(7, 1),
                new Cell(8, 1),
                new Cell(1, 2),
                new Cell(1, 3),
                new Cell(1, 4),
                new Cell(1, 5),
                new Cell(1, 6),
                new Cell(1, 7),
                new Cell(1, 8)
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMovesDownAndLeft() {
        Castle test = new Castle(new Cell(8, 8));
        Cell[] expectedMoves = new Cell[]{
                new Cell(8, 7),
                new Cell(8, 6),
                new Cell(8, 5),
                new Cell(8, 4),
                new Cell(8, 3),
                new Cell(8, 2),
                new Cell(8, 1),
                new Cell(7, 8),
                new Cell(6, 8),
                new Cell(5, 8),
                new Cell(4, 8),
                new Cell(3, 8),
                new Cell(2, 8),
                new Cell(1, 8)
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMovesUpAndLeft() {
        Castle test = new Castle(new Cell(1, 8));
        Cell[] expectedMoves = new Cell[]{
                new Cell(2, 8),
                new Cell(3, 8),
                new Cell(4, 8),
                new Cell(5, 8),
                new Cell(6, 8),
                new Cell(7, 8),
                new Cell(8, 8),
                new Cell(1, 7),
                new Cell(1, 6),
                new Cell(1, 5),
                new Cell(1, 4),
                new Cell(1, 3),
                new Cell(1, 2),
                new Cell(1, 1)
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMovesDownAndRight() {
        Castle test = new Castle(new Cell(8, 1));
        Cell[] expectedMoves = new Cell[]{
                new Cell(7, 1),
                new Cell(6, 1),
                new Cell(5, 1),
                new Cell(4, 1),
                new Cell(3, 1),
                new Cell(2, 1),
                new Cell(1, 1),
                new Cell(8, 2),
                new Cell(8, 3),
                new Cell(8, 4),
                new Cell(8, 5),
                new Cell(8, 6),
                new Cell(8, 7),
                new Cell(8, 8)
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMovesMixedDirections() {
        Castle test = new Castle(new Cell(4, 4));
        Cell[] expectedMoves = new Cell[]{
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
        Castle test = new Castle(new Cell(4, 4));
        test.way(new Cell(5, 5));
    }


    @Test
    public void wayShortest() {
        Castle test = new Castle(new Cell(3, 5));
        Cell[] expectedWay = new Cell[]{
                new Cell(4, 5)
        };
        Cell[] actualWay = test.way(new Cell(4, 5));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }

}
