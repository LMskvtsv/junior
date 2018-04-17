package ru.job4j.chessboard;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PawnTest {

    @Test
    public void possibleMoves() {
        Pawn test = new Pawn(new Cell(1, 1));
        Cell[] expectedMoves = new Cell[]{
                new Cell(2, 1)
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test
    public void possibleMoves2() {
        Pawn test = new Pawn(new Cell(7, 1));
        Cell[] expectedMoves = new Cell[]{
                new Cell(8, 1)
        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test(expected = ImpossibleMoveException.class)
    public void wayImpossibleMove() {
        Pawn test = new Pawn(new Cell(1, 1));
        test.way(new Cell(2, 2));
    }
}
