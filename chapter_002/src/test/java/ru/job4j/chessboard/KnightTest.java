package ru.job4j.chessboard;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class KnightTest {

    @Test
    public void possibleMovesUpRightOnly() {
        Knight test = new Knight(new Cell(1, 1));
        Cell[] expectedMoves = new Cell[]{
                new Cell(2, 3),
                new Cell(3, 2),
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMovesDownLeftOnly() {
        Knight test = new Knight(new Cell(8, 8));
        Cell[] expectedMoves = new Cell[]{
                new Cell(7, 6),
                new Cell(6, 7)
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMovesUpLeftOnly() {
        Knight test = new Knight(new Cell(1, 8));
        Cell[] expectedMoves = new Cell[]{
                new Cell(2, 6),
                new Cell(3, 7)
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMovesDownRightOnly() {
        Knight test = new Knight(new Cell(8, 1));
        Cell[] expectedMoves = new Cell[]{
                new Cell(7, 3),
                new Cell(6, 2),
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMovesMixedDirections() {
        Knight test = new Knight(new Cell(4, 4));
        Cell[] expectedMoves = new Cell[]{
                new Cell(5, 6),
                new Cell(5, 2),
                new Cell(6, 5),
                new Cell(6, 3),
                new Cell(3, 6),
                new Cell(3, 2),
                new Cell(2, 5),
                new Cell(2, 3),
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test(expected = ImpossibleMoveException.class)
    public void wayImpossibleMove() {
        Bishop test = new Bishop(new Cell(4, 4));
        test.way(new Cell(4, 2));
    }

    @Test
    public void wayUpRight() {
        Knight test = new Knight(new Cell(4, 4));
        Cell[] expectedWay = new Cell[]{
                new Cell(5, 4),
                new Cell(6, 4),
                new Cell(6, 5),
        };
        Cell[] actualWay = test.way(new Cell(6, 5));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }

    @Test
    public void wayUpLeft() {
        Knight test = new Knight(new Cell(4, 4));
        Cell[] expectedWay = new Cell[]{
                new Cell(5, 4),
                new Cell(6, 4),
                new Cell(6, 3),
        };
        Cell[] actualWay = test.way(new Cell(6, 3));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }

    @Test
    public void wayRightUP() {
        Knight test = new Knight(new Cell(4, 4));
        Cell[] expectedWay = new Cell[]{
                new Cell(4, 5),
                new Cell(4, 6),
                new Cell(5, 6),
        };
        Cell[] actualWay = test.way(new Cell(5, 6));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }

    @Test
    public void wayRightDown() {
        Knight test = new Knight(new Cell(4, 4));
        Cell[] expectedWay = new Cell[]{
                new Cell(4, 5),
                new Cell(4, 6),
                new Cell(3, 6),
        };
        Cell[] actualWay = test.way(new Cell(3, 6));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }

    @Test
    public void wayDownRight() {
        Knight test = new Knight(new Cell(4, 4));
        Cell[] expectedWay = new Cell[]{
                new Cell(3, 4),
                new Cell(2, 4),
                new Cell(2, 5),
        };
        Cell[] actualWay = test.way(new Cell(2, 5));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }

    @Test
    public void wayDownLeft() {
        Knight test = new Knight(new Cell(4, 4));
        Cell[] expectedWay = new Cell[]{
                new Cell(3, 4),
                new Cell(2, 4),
                new Cell(2, 3),
        };
        Cell[] actualWay = test.way(new Cell(2, 3));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }
    @Test
    public void wayLeftUp() {
        Knight test = new Knight(new Cell(4, 4));
        Cell[] expectedWay = new Cell[]{
                new Cell(4, 3),
                new Cell(4, 2),
                new Cell(5, 2),
        };
        Cell[] actualWay = test.way(new Cell(5, 2));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }

    @Test
    public void wayLeftDown() {
        Knight test = new Knight(new Cell(4, 4));
        Cell[] expectedWay = new Cell[]{
                new Cell(4, 3),
                new Cell(4, 2),
                new Cell(3, 2),
        };
        Cell[] actualWay = test.way(new Cell(3, 2));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }
}
