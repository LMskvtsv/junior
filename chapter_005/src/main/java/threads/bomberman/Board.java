package threads.bomberman;

import java.util.ArrayList;
import java.util.LinkedList;
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
     * @param man - bomberman to move.
     */
    public void move(BomberMan man) {
        Cell currentCell = man.getCurrentCell();
        Queue<Cell> possibleMoves = generateAllPossibleMoves(currentCell);
        Cell newCell = null;
        if (possibleMoves.size() > 0) {
            newCell = possibleMoves.poll();
        } else {
            System.out.printf("Game over for %s! There are no possible moves%s", Thread.currentThread().getName(), System.lineSeparator());
            Thread.currentThread().interrupt();
        }
        ReentrantLock newLock = board[newCell.getW()][newCell.getH()];
        ReentrantLock oldLock = board[currentCell.getW()][currentCell.getH()];

        try {
            while(!newLock.tryLock(500, TimeUnit.MILLISECONDS)) {
                System.out.printf("Thread %s: cell [%d][%d] is still locked, choosing another one.%s", Thread.currentThread().getName(), newCell.getW(), newCell.getH(), System.lineSeparator());
                if (possibleMoves.size() > 0) {
                    newCell = possibleMoves.poll();
                    newLock = board[newCell.getW()][newCell.getH()];
                } else {
                    System.out.printf("Game over for %s! There are no possible moves%s", Thread.currentThread().getName(), System.lineSeparator());
                    Thread.currentThread().interrupt();
                }
                System.out.printf("Thread %s: new cell [%d][%d] was chosen.%s", Thread.currentThread().getName(), newCell.getW(), newCell.getH(), System.lineSeparator());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(oldLock.isLocked()) {
            oldLock.unlock();
        }
        man.setCurrentCell(newCell);
        System.out.printf("BomberMan %s moved from cell[%d][%d] to cell [%d][%d]%s",
                man.getName(),
                currentCell.getW(),
                currentCell.getH(),
                newCell.getW(),
                newCell.getH(),
                System.lineSeparator());
    }

    /**
     * Calculates possible moves in accordance with current position.
     * @param currentCell
     * @return
     */
    private Queue<Cell> generateAllPossibleMoves(Cell currentCell) {
        Queue<Cell> possibleMoves = new LinkedList<Cell>();
        possibleMoves.add(new Cell(currentCell.getW() + 1, currentCell.getH()));
        possibleMoves.add(new Cell(currentCell.getW(), currentCell.getH() + 1));
        possibleMoves.add(new Cell(currentCell.getW() - 1, currentCell.getH()));
        possibleMoves.add(new Cell(currentCell.getW(), currentCell.getH() - 1));
        ArrayList<Cell> movesToRemove = new ArrayList(8);
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
