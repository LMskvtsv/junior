package ru.job4j.chessboard;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BishopTest {

    @Test
    public void possibleMovesUpRightOnly() {
        Bishop test = new Bishop(new Cell(1, 1));
        Cell[] expectedMoves = new Cell[]{
                new Cell(2, 2),
                new Cell(3, 3),
                new Cell(4, 4),
                new Cell(5, 5),
                new Cell(6, 6),
                new Cell(7, 7),
                new Cell(8, 8)
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMovesDownLeftOnly() {
        Bishop test = new Bishop(new Cell(8, 8));
        Cell[] expectedMoves = new Cell[]{
                new Cell(7, 7),
                new Cell(6, 6),
                new Cell(5, 5),
                new Cell(4, 4),
                new Cell(3, 3),
                new Cell(2, 2),
                new Cell(1, 1)
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMovesUpLeftOnly() {
        Bishop test = new Bishop(new Cell(1, 8));
        Cell[] expectedMoves = new Cell[]{
                new Cell(2, 7),
                new Cell(3, 6),
                new Cell(4, 5),
                new Cell(5, 4),
                new Cell(6, 3),
                new Cell(7, 2),
                new Cell(8, 1)
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMovesDownRightOnly() {
        Bishop test = new Bishop(new Cell(8, 1));
        Cell[] expectedMoves = new Cell[]{
                new Cell(7, 2),
                new Cell(6, 3),
                new Cell(5, 4),
                new Cell(4, 5),
                new Cell(3, 6),
                new Cell(2, 7),
                new Cell(1, 8)
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMovesMixedDirections() {
        Bishop test = new Bishop(new Cell(4, 7));
        Cell[] expectedMoves = new Cell[]{
                new Cell(5, 8),
                new Cell(5, 6),
                new Cell(6, 5),
                new Cell(7, 4),
                new Cell(8, 3),
                new Cell(3, 8),
                new Cell(3, 6),
                new Cell(2, 5),
                new Cell(1, 4),
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test(expected = ImpossibleMoveException.class)
    public void wayImpossibleMove() {
        Bishop test = new Bishop(new Cell(4, 7));
        test.way(new Cell(5, 7));
    }

    @Test
    public void wayLongestUpRight() {
        Bishop test = new Bishop(new Cell(1, 1));
        Cell[] expectedWay = new Cell[]{
                new Cell(2, 2),
                new Cell(3, 3),
                new Cell(4, 4),
                new Cell(5, 5),
                new Cell(6, 6),
                new Cell(7, 7),
                new Cell(8, 8)
        };
        Cell[] actualWay = test.way(new Cell(8, 8));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }

    @Test
    public void wayShortestUpLeft() {
        Bishop test = new Bishop(new Cell(3, 5));
        Cell[] expectedWay = new Cell[]{
                new Cell(4, 4)
        };
        Cell[] actualWay = test.way(new Cell(4, 4));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }

    @Test
    public void way2CellsDownRight() {
        Bishop test = new Bishop(new Cell(5, 3));
        Cell[] expectedWay = new Cell[]{
                new Cell(4, 4),
                new Cell(3, 5)
        };
        Cell[] actualWay = test.way(new Cell(3, 5));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }

    @Test
    public void way3CellsDownLeft() {
        Bishop test = new Bishop(new Cell(5, 3));
        Cell[] expectedWay = new Cell[]{
                new Cell(4, 4),
                new Cell(3, 5)
        };
        Cell[] actualWay = test.way(new Cell(3, 5));
        for (int i = 0; i < actualWay.length; i++) {
            assertThat(actualWay[i].getX(), is(expectedWay[i].getX()));
            assertThat(actualWay[i].getY(), is(expectedWay[i].getY()));
        }
    }
}
