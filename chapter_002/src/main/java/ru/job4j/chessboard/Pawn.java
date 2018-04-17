package ru.job4j.chessboard;

import java.util.Arrays;
import java.util.Objects;

class Pawn extends Figure {

    /**
     * With the creation of new Pawn object all possible moves are calculated automatically.
     *
     * @param position - starting position
     */
    Pawn(Cell position) {
        super(position);
    }

    /**
     * Generates all possible moves in accordance with the initial position.
     * When all possible moves were generated, remove all empty possibleMoves array's cells.
     */
    @Override
    protected void generatePossibleMoves() {
        int x = position.getX();
        if (x < Cell.MAX_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(++x, position.getY());
        } else {
            System.out.println("Cannot move further. Need to be replaced with another figure.");
        }
        possibleMoves = Arrays.stream(possibleMoves).filter(Objects::nonNull).toArray(Cell[]::new);
    }

    /**
     * Calculates way for pawn. Way is always consist of one cell (because of the task).
     *
     * @param dest - destination position
     * @return all cells that will be passed on desirable way, including destination cell.
     * @throws ru.job4j.chessboard.ImpossibleMoveException if destination cell was not found in possibleMoves array.
     */
    @Override
    public Cell[] way(Cell dest) throws ImpossibleMoveException {
        Cell[] way = new Cell[maxWayLength];
        if (dest.getX() == possibleMoves[0].getX() && dest.getY() == possibleMoves[0].getY()) {
            way[0] = new Cell(dest.getX(), dest.getY());
        } else {
            throw new ImpossibleMoveException("Impossible move. Choose another one.");
        }
        return Arrays.stream(way).filter(Objects::nonNull).toArray(Cell[]::new);
    }

    /**
     * Creates new Pawn object with specified position.
     *
     * @param dest - position on the board.
     * @return - new Pawn object with the specified position.
     */
    @Override
    public Figure copy(Cell dest) {
        return new Pawn(dest);
    }
}
