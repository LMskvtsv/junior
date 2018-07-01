package threads.bomberman;

import java.util.Queue;

public class MonsterTask implements Runnable {
    Monster monster;
    Board board;

    public MonsterTask(Monster man, Board board) {
        this.monster = man;
        this.board = board;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Queue<Cell> moves = board.generateAllPossibleMoves(monster.getCurrentCell());
            Cell newCell = moves.poll();
            while (!board.move(monster.getCurrentCell(), newCell)) {
                System.out.printf("Monster %s thread %s: cell[%d][%d] was locked, choose another one.%s",
                        monster.getName(), Thread.currentThread(), newCell.getW(), newCell.getH(), System.lineSeparator());
                newCell = moves.poll();
                System.out.printf("Monster %s thread %s: new cell[%d][%d] was chosen.%s",
                        monster.getName(), Thread.currentThread(), newCell.getW(), newCell.getH(), System.lineSeparator());
            }
            System.out.printf("Monster %s thread %s: moved from cell[%d][%d] to cell [%d][%d]%s",
                    monster.getName(),
                    Thread.currentThread(),
                    monster.getCurrentCell().getW(),
                    monster.getCurrentCell().getH(),
                    newCell.getW(),
                    newCell.getH(),
                    System.lineSeparator());
            monster.setCurrentCell(newCell);

            try {
                Thread.currentThread().sleep(1_000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

