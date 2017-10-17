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
    public String paintWithoutHint(int width, int height) {
        StringBuilder builder = new StringBuilder();
        for (int z = 1; z <= width * height; z++) {
            if (z % 2 == 0) {
                builder.append(" ");
            } else {
                builder.append("X");
            }
            if (z % (width) == 0) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    /**
     * Prints chess board.
     *
     * @param width  - should be uneven.
     * @param height - height of the chessboard.
     * @return String for console output.
     */
    public String paintWithHint(int width, int height) {
        if (width % 2 == 0) {
            return "Width should be uneven!";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) {
                    builder.append("X");
                } else {
                    builder.append(" ");
                }
            }
            builder.append(System.getProperty("line.separator"));
        }
        return builder.toString();
    }
}