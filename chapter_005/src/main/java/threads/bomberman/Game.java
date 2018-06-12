package threads.bomberman;

public class Game {
    public static void main(String[] args) {
        final Board board = new Board(5, 5);
        final BomberMan white = new BomberMan(new Cell(0, 0), "White");
        final BomberMan black = new BomberMan(new Cell(4, 4), "Black");
        board.init();
        Thread whiteBomberManMove = new Thread() {
            @Override
            public void run() {
                while (true) {
                    board.move(white);
                    try {
                        Thread.currentThread().sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        whiteBomberManMove.setName("White bomberman");
        whiteBomberManMove.start();

        Thread blackBomberManMove = new Thread() {
            @Override
            public void run() {
                while (true) {
                    board.move(black);
                    try {
                        Thread.currentThread().sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        blackBomberManMove.setName("Black bomberman");
        blackBomberManMove.start();
        boolean needToContinue = false;
        while (!whiteBomberManMove.isInterrupted() || !blackBomberManMove.isInterrupted()) {
            needToContinue = true;
            // watching the game
        }
        System.out.println("Game over! " + needToContinue);
    }
}
