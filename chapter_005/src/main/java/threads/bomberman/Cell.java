package threads.bomberman;

public class Cell {

    private final int w;
    private final int h;

    public Cell(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    private boolean isLocked = false;

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
