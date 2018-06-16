package threads.bomberman;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    public static void main(String[] args) {
        final Board board = new Board(4, 4);
        final BomberMan white = new BomberMan(new Cell(0, 0), "White");
        final BomberMan black = new BomberMan(new Cell(3, 3), "Black");
        board.init();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new BomberManTask(white, board));
        executorService.execute(new BomberManTask(black, board));
        try {
            Thread.currentThread().sleep(30_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(0);
        }
        executorService.shutdownNow();
    }
}
