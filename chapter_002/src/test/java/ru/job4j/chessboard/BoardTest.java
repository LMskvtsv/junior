package ru.job4j.chessboard;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BoardTest {
    @Test
    public void boardAddFigures() {
        Board board = new Board();
        Bishop b1 = new Bishop(new Cell(1, 1));
        Bishop b2 = new Bishop(new Cell(5, 3));
        board.add(b1);
        board.add(b2);
        assertThat(board.figures[0], is(b1));
        assertThat(board.figures[1], is(b2));
    }

    @Test
    public void boardMoveSuccess() {
        Board board = new Board();
        Bishop b1 = new Bishop(new Cell(4, 2));
        board.add(b1);
        assertThat(true, is(board.move(new Cell(4, 2), new Cell(1, 5))));
        assertThat(board.figures[0].position.getX(), is(1));
        assertThat(board.figures[0].position.getY(), is(5));
    }

    @Test(expected = FigureNotFoundException.class)
    public void boardMoveFigureNotFoundEmptyBoard() {
        Board board = new Board();
        board.move(new Cell(1, 1), new Cell(8, 8));
    }

    @Test(expected = FigureNotFoundException.class)
    public void boardMoveFigureNotFoundEmptyCell() {
        Board board = new Board();
        board.add(new Bishop(new Cell(2, 2)));
        board.move(new Cell(1, 1), new Cell(8, 8));
    }

    @Test(expected = ImpossibleMoveException.class)
    public void boardMoveImpossibleMove() {
        Board board = new Board();
        board.add(new Bishop(new Cell(2, 2)));
        board.move(new Cell(2, 2), new Cell(2, 1));
    }

    @Test(expected = OccupiedWayException.class)
    public void boardMoveOccupiedDestCell() {
        Board board = new Board();
        board.add(new Bishop(new Cell(1, 1)));
        board.add(new Bishop(new Cell(4, 4)));
        board.move(new Cell(1, 1), new Cell(4, 4));
    }

    @Test(expected = OccupiedWayException.class)
    public void boardMoveOccupiedMiddleWay() {
        Board board = new Board();
        board.add(new Bishop(new Cell(1, 1)));
        board.add(new Bishop(new Cell(4, 4)));
        board.move(new Cell(1, 1), new Cell(8, 8));
    }
}
