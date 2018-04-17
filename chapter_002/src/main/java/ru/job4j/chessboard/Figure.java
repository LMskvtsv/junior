package ru.job4j.chessboard;

public abstract class Figure {

    final Cell position;
    final int maxWayLength = 8;
    protected Cell[] possibleMoves = new Cell[64];
    protected int movesCounter = 0;

    /**
     * With the creation of new Figure object all possible moves are calculated automatically.
     *
     * @param position - starting position.
     */
    Figure(Cell position) {
        this.position = position;
        generatePossibleMoves();
    }

    protected abstract void generatePossibleMoves();

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
     * @return - new object with specified position.
     */
    public abstract Figure copy(Cell dest);
}
