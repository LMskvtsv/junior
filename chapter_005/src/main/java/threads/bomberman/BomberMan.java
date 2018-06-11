package threads.bomberman;

public class BomberMan {
    private Cell currentCell;
    private final String name;

    public String getName() {
        return name;
    }

    public BomberMan(Cell startingCell, String name) {
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
