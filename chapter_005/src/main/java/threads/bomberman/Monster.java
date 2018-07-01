package threads.bomberman;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Monster {
    private Cell currentCell;
    private final String name;
    private BlockingQueue<Cell> userMoves  = new LinkedBlockingDeque<>(1);

    public String getName() {
        return name;
    }

    public Monster(Cell startingCell, String name) {
        currentCell = startingCell;
        this.name = name;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

}
