package ru.job4j.chessboard;


import java.util.Arrays;
import java.util.Objects;

class Board {

    Figure[] figures = new Figure[32];
    private int counter = 0;

    /**
     * Adds figure on the board. There will be a message in case there is no empty space on the board.
     *
     * @param figure - figure to add.
     */
    void add(Figure figure) {
        try {
            figures[counter++] = figure;
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("All cells are occupied. Cannot add one more figure.");
        }
    }

    /**
     * Move figure on the the board from starting position to destination position.
     *
     * @param source - starting position
     * @param dest   - destination position
     * @return true - if moving was successful.
     * @throws ImpossibleMoveException - in case destination cell was not found in possibleMoves array.
     * @throws OccupiedWayException    - in case there is another figure on the way (middle way and destination point).
     * @throws FigureNotFoundException - in case board or cell is empty.
     */
    boolean move(Cell source, Cell dest) throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        boolean isExist = false;
        Figure figure = null;
        int position = 0;
        Figure[] cleanedFigures = Arrays.stream(figures).filter(Objects::nonNull).toArray(Figure[]::new);
        if (cleanedFigures.length == 0) {
            throw new FigureNotFoundException("Figure not found. Empty cell.");
        } else {
            for (int i = 0; i < cleanedFigures.length; i++) {
                if (cleanedFigures[i].position.getX() == source.getX() && cleanedFigures[i].position.getY() == source.getY()) {
                    isExist = true;
                    figure = cleanedFigures[i];
                    position = i;
                    break;
                }
            }
        }
        if (!isExist) {
            throw new FigureNotFoundException("Figure not found. Empty cell.");
        } else {
            for (Cell cell : figure.way(dest)) {
                for (Figure f : cleanedFigures) {
                    if (cell.getX() == f.position.getX() && cell.getY() == f.position.getY()) {
                        throw new OccupiedWayException("There is occupied cell on the given way! Try another move.");
                    }
                }
            }
        }
        figures[position] = figure.copy(dest);
        return true;
    }
}

