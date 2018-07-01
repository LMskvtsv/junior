package threads.bomberman;

import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    static ExecutorService executorService;
    static Board board;
    static final Logger LOG = Logger.getLogger(Game.class);
    public static void main(String[] args) {
        board = new Board(5, 5);
        final BomberMan white = new BomberMan(new Cell(0, 2), "White");
        board.init();
        executorService = Executors.newCachedThreadPool();
        executorService.execute(new BomberManTask(white, board));
        addMonsters();
        try {
            Thread.currentThread().sleep(30_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(0);
        }
        executorService.shutdownNow();
    }

    private static void addMonsters() {
        int monstersQtty = ThreadLocalRandom.current().nextInt(2,  5 + 1);
        for(int i = 0; i < monstersQtty; i++){
            Cell startingCell = new Cell(i, i);
            if(!board.getRocks().contains(startingCell)) {
                Monster m = new Monster(new Cell(i, i), "Monster " + i);
                LOG.info(String.format("Monster %d was started.", i));
                executorService.execute(new MonsterTask(m, board));
            }
        }
    }
}
