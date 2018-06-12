package threads.bomberman;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Board {

    private final Object lock = new Object();

    private final Cell[][] board;

    public Board(int width, int height) {
        this.board = new Cell[width][height];
    }

    public void init() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
    }

    public void move(BomberMan man) {
        Cell currentCell = man.getCurrentCell();
        Queue<Cell> possibleMoves = generateAllPosiibleMoves(currentCell);
        Cell newCell = null;
        if (possibleMoves.size() > 0) {
            newCell = possibleMoves.poll();
        } else {
            System.out.printf("Game over for %s! There are no possible moves%s", Thread.currentThread().getName(), System.lineSeparator());
            Thread.currentThread().interrupt();
        }
        synchronized (lock) {
            System.out.println("Thread " + Thread.currentThread().getName() + " is in critical section.");
            while (board[newCell.getW()][newCell.getH()].isLocked()) {
                try {
                    lock.wait(500);
                    System.out.println("Thread " + Thread.currentThread().getName() + ".");
                    System.out.printf("Cell [%d][%d] is locked, waiting 500 ms.%s", newCell.getW(), newCell.getH(), System.lineSeparator());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (board[newCell.getW()][newCell.getH()].isLocked()) {
                    System.out.println("Thread " + Thread.currentThread().getName() + ".");
                    System.out.printf("Cell [%d][%d] is still locked, choosing another one.%s", newCell.getW(), newCell.getH(), System.lineSeparator());
                    if (possibleMoves.size() > 0) {
                        newCell = possibleMoves.poll();
                    } else {
                        System.out.printf("Game over for %s! There are no possible moves%s", Thread.currentThread().getName(), System.lineSeparator());
                        Thread.currentThread().interrupt();
                    }
                    System.out.printf("New cell [%d][%d] was chosen.%s", newCell.getW(), newCell.getH(), System.lineSeparator());
                } else {
                    System.out.println("Thread " + Thread.currentThread().getName() + ".");
                    System.out.printf("Cell [%d][%d] is free now, go for it.%s", newCell.getW(), newCell.getH(), System.lineSeparator());
                    return;
                }
            }
            newCell.setLocked(true);
            board[currentCell.getW()][currentCell.getH()].setLocked(false);
            board[newCell.getW()][newCell.getH()] = newCell;
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

    private Queue<Cell> generateAllPosiibleMoves(Cell currentCell) {
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
