package ru.job4j.chessboard;

import java.util.Arrays;
import java.util.Objects;

class Knight extends Figure {

    /**
     * With the creation of new Bishop object all possible moves are calculated automatically.
     * @param position - starting position.
     */
    Knight(Cell position) {
        super(position);
    }

    /**
     * Generates all possible moves in accordance with the initial position.
     * When all possible moves were generated, remove all empty possibleMoves array's cells.
     */
    @Override
    protected void generatePossibleMoves() {
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
    private void calculateUpLeftMoves(int x, int y) {
        x = x + 2;
        int y1 = y + 1;
        int y2 = y - 1;
        if (x <= Cell.MAX_CELL_NUMBER && y1 <= Cell.MAX_CELL_NUMBER && y1 >= Cell.MIN_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(x, y1);
        }
        if (x <= Cell.MAX_CELL_NUMBER && y2 <= Cell.MAX_CELL_NUMBER && y2 >= Cell.MIN_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(x, y2);
        }
    }

    /**
     * Generates possible moves in up-left direction from initial position and adds them to possibleMoves array.
     * @param x - vertical coordinate of initial position
     * @param y - horizontal coordinate of initial position
     */
    private void calculateUpRightMoves(int x, int y) {
        x = x + 1;
        int y1 = y + 2;
        int y2 = y - 2;
        if (x <= Cell.MAX_CELL_NUMBER && y1 <= Cell.MAX_CELL_NUMBER && y1 >= Cell.MIN_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(x, y1);
        }
        if (x <= Cell.MAX_CELL_NUMBER && y2 <= Cell.MAX_CELL_NUMBER && y2 >= Cell.MIN_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(x, y2);
        }
    }

    /**
     * Generates possible moves in down-right direction from initial position and adds them to possibleMoves array.
     * @param x - vertical coordinate of initial position
     * @param y - horizontal coordinate of initial position
     */
    private void calculateDownRightMoves(int x, int y) {
        x = x - 1;
        int y1 = y + 2;
        int y2 = y - 2;
        if (x >= Cell.MIN_CELL_NUMBER && y1 <= Cell.MAX_CELL_NUMBER && y1 >= Cell.MIN_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(x, y1);
        }
        if (x >= Cell.MIN_CELL_NUMBER && y2 <= Cell.MAX_CELL_NUMBER && y2 >= Cell.MIN_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(x, y2);
        }
    }

    /**
     * Generates possible moves in down-left direction from initial position and adds them to possibleMoves array.
     * @param x - vertical coordinate of initial position
     * @param y - horizontal coordinate of initial position
     */
    private void calculateDownLeftMoves(int x, int y) {
        x = x - 2;
        int y1 = y + 1;
        int y2 = y - 1;
        if (x >= Cell.MIN_CELL_NUMBER && y1 <= Cell.MAX_CELL_NUMBER && y1 >= Cell.MIN_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(x, y1);
        }
        if (x >= Cell.MIN_CELL_NUMBER && y2 <= Cell.MAX_CELL_NUMBER && y2 >= Cell.MIN_CELL_NUMBER) {
            possibleMoves[movesCounter++] = new Cell(x, y2);
        }
    }


    /**
     * Calculates way for bishop.
     * @param dest - destination position
     * @return all cells that will be passed on desirable way, including destination cell.
     * @throws ru.job4j.chessboard.ImpossibleMoveException if destination cell was not found in possibleMoves array.
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
            if (Math.abs(dest.getX() - start.getX()) == 2  && startX < dest.getX()) {
                way[counter++] = new Cell(++startX, startY);
                way[counter++] = new Cell(++startX, startY);
            } else if (Math.abs(dest.getX() - start.getX()) == 2 && startX > dest.getX()) {
                way[counter++] = new Cell(--startX, startY);
                way[counter++] = new Cell(--startX, startY);
            } else if (Math.abs(dest.getX() - start.getX()) == 1 && start.getY() < dest.getY()) {
                way[counter++] = new Cell(startX, ++startY);
                way[counter++] = new Cell(startX, ++startY);
            } else if (Math.abs(dest.getX() - start.getX()) == 1 && start.getY() > dest.getY()) {
                way[counter++] = new Cell(startX, --startY);
                way[counter++] = new Cell(startX, --startY);
            }
            way[counter++] = new Cell(dest.getX(), dest.getY());
        }
        return Arrays.stream(way).filter(Objects::nonNull).toArray(Cell[]::new);
    }

    /**
     * Creates new Bishop object with specified position.
     * @param dest - position on the board.
     * @return - new Bishop object
     */
    @Override
    public Figure copy(Cell dest) {
        return new Knight(dest);
    }
}
