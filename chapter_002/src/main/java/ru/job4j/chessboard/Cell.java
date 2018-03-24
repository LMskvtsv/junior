package ru.job4j.chessboard;

class Cell {

    private int x;
    private int y;
    static final int MAX_CELL_NUMBER = 8;
    static final int MIN_CELL_NUMBER = 1;

    /**
     * Cell can be created only in case vertical position and horizontal position are correct.
     * @param x - vertical position
     * @param y - horizontal position
     * @throws ImpossibleCellException - in case any position less than 1 and more than 8.
     */
    Cell(int x, int y) throws ImpossibleCellException {
        if (x >= MIN_CELL_NUMBER
                && x <= MAX_CELL_NUMBER
                && y >= MIN_CELL_NUMBER
                && y <= MAX_CELL_NUMBER) {
            this.x = x;
            this.y = y;
        } else {
            throw new ImpossibleCellException("X and Y must be between " + MIN_CELL_NUMBER + " and " + MAX_CELL_NUMBER);
        }
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}
