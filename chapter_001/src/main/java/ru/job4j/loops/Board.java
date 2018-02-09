package ru.job4j.loops;

/**
 * Class for board printing.
 */
public class Board {

    /**
     * Prints chess board.
     *
     * @param width  - should be uneven.
     * @param height - height of the chessboard.
     * @return String for console output.
     */
    public String paint(int width, int height) {
        if (width % 2 == 0) {
            return "Width should be uneven!";
        }
        StringBuilder board = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) {
                    board.append("X");
                } else {
                    board.append(" ");
                }
            }
            board.append(System.getProperty("line.separator"));
        }
        return board.toString();
    }
}