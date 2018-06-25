package threads.bomberman;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return w == cell.w
                && h == cell.h;
    }

    @Override
    public int hashCode() {
        return Objects.hash(w, h);
    }
}
