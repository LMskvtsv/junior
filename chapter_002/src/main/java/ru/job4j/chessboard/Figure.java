package ru.job4j.chessboard;

public abstract class Figure {

    final Cell position;
    final int maxWayLength = 8;

    Figure(Cell position) {
        this.position = position;
    }

    /**
     * Calculates way of the figure from current position to destination position.
     * @param dest - destination position
     * @return all cells that will be passed on desirable way, including destination cell.
     * @throws ImpossibleMoveException if destination cell was not found in possibleMoves array.
     */
    public abstract Cell[] way(Cell dest) throws ImpossibleMoveException;

    /**
     * Creates new Figure object with specified position.
     * @param dest - position on the board.
     * @return
     */
    public abstract Figure copy(Cell dest);
}
