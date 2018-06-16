package threads.bomberman;

import java.util.Queue;

public class BomberManTask implements Runnable {
    BomberMan man;
    Board board;

    public BomberManTask(BomberMan man, Board board) {
        this.man = man;
        this.board = board;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Queue<Cell> moves = board.generateAllPossibleMoves(man.getCurrentCell());
            Cell newCell = moves.poll();
            while (!board.move(man.getCurrentCell(), newCell)) {
                System.out.printf("BomberMan %s thread %s: cell[%d][%d] was locked, choose another one.%s",
                        man.getName(), Thread.currentThread(), newCell.getW(), newCell.getH(), System.lineSeparator());
                newCell = moves.poll();
                System.out.printf("BomberMan %s thread %s: new cell[%d][%d] was chosen.%s",
                        man.getName(), Thread.currentThread(), newCell.getW(), newCell.getH(), System.lineSeparator());
            }
            System.out.printf("BomberMan %s thread %s: moved from cell[%d][%d] to cell [%d][%d]%s",
                    man.getName(),
                    Thread.currentThread(),
                    man.getCurrentCell().getW(),
                    man.getCurrentCell().getH(),
                    newCell.getW(),
                    newCell.getH(),
                    System.lineSeparator());
            man.setCurrentCell(newCell);

            try {
                Thread.currentThread().sleep(1_000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

