package threads.bomberman;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Bomberman game, initial version.
 */
public class Board {

    private final ReentrantLock[][] board;

    public Board(int width, int height) {
        this.board = new ReentrantLock[width][height];
    }

    /**
     * Board initialisation with reentrant locks for every cell.
     */
    public void init() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new ReentrantLock();
            }
        }
    }

    /**
     * Checks if possible move is available. If possible move is locked by another character, then bomberman will wait 500 ms and new move will be
     * chosen.
     *
     * @param source - move from
     * @param dest   - move to
     */
    public boolean move(Cell source, Cell dest) {
        boolean result = true;
        Queue<Cell> possibleMoves = generateAllPossibleMoves(source);
        if (possibleMoves.contains(dest)) {
            ReentrantLock newLock = board[dest.getW()][dest.getH()];
            ReentrantLock oldLock = board[source.getW()][source.getH()];
            try {
                while (!newLock.tryLock(500, TimeUnit.MILLISECONDS)) {
                    System.out.printf("Thread %s: cell [%d][%d] is still locked, choose another one.%s",
                            Thread.currentThread(),
                            dest.getW(),
                            dest.getH(),
                            System.lineSeparator());
                    result = false;
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (result && oldLock.isLocked()) {
                oldLock.unlock();
            }
        } else {
            System.out.printf("Impossible move, choose another one.%s", System.lineSeparator());
            result = false;
        }
        return result;
    }

    /**
     * Calculates possible moves in accordance with current position.
     *
     * @param currentCell
     * @return
     */
    public Queue<Cell> generateAllPossibleMoves(Cell currentCell) {
        Queue<Cell> possibleMoves = new LinkedList<>();
        possibleMoves.add(new Cell(currentCell.getW() + 1, currentCell.getH()));
        possibleMoves.add(new Cell(currentCell.getW(), currentCell.getH() + 1));
        possibleMoves.add(new Cell(currentCell.getW() - 1, currentCell.getH()));
        possibleMoves.add(new Cell(currentCell.getW(), currentCell.getH() - 1));
        ArrayList<Cell> movesToRemove = new ArrayList<>();
        for (Cell cell : possibleMoves) {
            if (cell.getW() < 0
                    || cell.getW() >= board.length
                    || cell.getH() < 0
                    || cell.getH() >= board[0].length) {
                movesToRemove.add(cell);
            }
        }
        possibleMoves.removeAll(movesToRemove);
        return possibleMoves;
    }
}
