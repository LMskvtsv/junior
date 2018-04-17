package ru.job4j.chessboard;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class KingTest {

    @Test
    public void possibleMovesMixedDirections() {
        King test = new King(new Cell(4, 4));
        Cell[] expectedMoves = new Cell[]{
                new Cell(5, 5),
                new Cell(5, 3),
                new Cell(3, 5),
                new Cell(3, 3),
                new Cell(5, 4),
                new Cell(4, 3),
                new Cell(3, 4),
                new Cell(4, 5),

        };
        for (int i = 0; i < test.possibleMoves.length; i++) {
            assertThat(test.possibleMoves[i].getX(), is(expectedMoves[i].getX()));
            assertThat(test.possibleMoves[i].getY(), is(expectedMoves[i].getY()));
        }
    }

    @Test(expected = ImpossibleMoveException.class)
    public void wayImpossibleMove() {
        King test = new King(new Cell(4, 4));
        test.way(new Cell(6, 6));
    }

}
