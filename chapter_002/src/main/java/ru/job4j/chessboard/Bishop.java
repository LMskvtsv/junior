package ru.job4j.chessboard;

import java.util.Arrays;
import java.util.Objects;

class Bishop extends Figure {

    Cell[] possibleMoves = new Cell[64];

    /**
     * With the creation of new Cell object all possible moves are calculated automatically.
     * @param position
     */
    Bishop(Cell position) {
        super(position);
        generatePossibleMoves();
    }

    private int movesCounter = 0;

    /**
     * Generates all possible moves in accordance with the initial position.
     * When all possible moves were generated, remove all empty possibleMoves array's cells.
     */
    private void generatePossibleMoves() {
        calculateUpRightMoves(position.getX(), position.getY());
        calculateUpLeftMoves(position.getX(), position.getY());
        calculateDownRightMoves(position.getX(), position.getY());
        calculateDownLeftMoves(position.getX(), position.getY());
        possibleMoves = Arrays.stream(possibleMoves).filter(Objects::nonNull).toArray(Cell[]::new);
    }

    /**
     * Generates possible moves in up-right direction from initial position and adds them to possibleMoves array.
     * @param x - vertical coordinate of initial position
     * @param y - horizontal coordinate of initial position
     */
    private void calculateUpRightMoves(int x, int y) {
        while (x < Cell.MAX_CELL_NUMBER && y < Cell.MAX_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(++x, ++y);
        }
    }

    /**
     * Generates possible moves in up-left direction from initial position and adds them to possibleMoves array.
     * @param x - vertical coordinate of initial position
     * @param y - horizontal coordinate of initial position
     */
    private void calculateUpLeftMoves(int x, int y) {
        while (x < Cell.MAX_CELL_NUMBER && y > Cell.MIN_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(++x, --y);
        }
    }

    /**
     * Generates possible moves in down-right direction from initial position and adds them to possibleMoves array.
     * @param x - vertical coordinate of initial position
     * @param y - horizontal coordinate of initial position
     */
    private void calculateDownRightMoves(int x, int y) {
        while (x > Cell.MIN_CELL_NUMBER && y < Cell.MAX_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(--x, ++y);
        }
    }

    /**
     * Generates possible moves in down-left direction from initial position and adds them to possibleMoves array.
     * @param x - vertical coordinate of initial position
     * @param y - horizontal coordinate of initial position
     */
    private void calculateDownLeftMoves(int x, int y) {
        while (x > Cell.MIN_CELL_NUMBER && y > Cell.MIN_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(--x, --y);
        }
    }

    /**
     * Calculates way for bishop.
     * @param dest - destination position
     * @return all cells that will be passed on desirable way, including destination cell.
     * @throws ImpossibleMoveException if destination cell was not found in possibleMoves array.
     */
    @Override
    public Cell[] way(Cell dest) throws ImpossibleMoveException {
        Cell[] way = new Cell[maxWayLength];
        Cell start = this.position;
        boolean isPossible = false;
        for (Cell move : possibleMoves) {
            if (dest.getX() == move.getX() && dest.getY() == move.getY()) {
                isPossible = true;
            }
        }
        if (!isPossible) {
            throw new ImpossibleMoveException("Impossible move. Choose another one.");
        } else {
            int counter = 0;
            int startX = start.getX();
            int startY = start.getY();
            int destX = dest.getX();
            if (start.getX() < dest.getX() && start.getY() < dest.getY()) {
                while (startX != destX) {
                    way[counter++] = new Cell(++startX, ++startY);
                }
            } else if (start.getX() > dest.getX() && start.getY() > dest.getY()) {
                while (startX != destX) {
                    way[counter++] = new Cell(--startX, --startY);
                }
            } else if (start.getX() < dest.getX() && start.getY() > dest.getY()) {
                while (startX != destX) {
                    way[counter++] = new Cell(++startX, --startY);
                }
            } else if (start.getX() > dest.getX() && start.getY() < dest.getY()) {
                while (startX != destX) {
                    way[counter++] = new Cell(--startX, ++startY);
                }
            }
        }
        return Arrays.stream(way).filter(Objects::nonNull).toArray(Cell[]::new);
    }

    /**
     * Creates new Bishop object with specified position.
     * @param dest - position on the board.
     * @return
     */
    @Override
    public Figure copy(Cell dest) {
        return new Bishop(dest);
    }
}
