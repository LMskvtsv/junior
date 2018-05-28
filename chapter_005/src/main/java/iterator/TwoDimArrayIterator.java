package iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwoDimArrayIterator implements Iterator {

    private int[][] values;
    private int rowIndex = 0;
    private int columnIndex = 0;

    public TwoDimArrayIterator(int[][] values) {
        this.values = values;
    }

    /**
     * Checks if array has next value.
     *
     * @return false in case array is empty, or current value is last value from last array.
     */
    @Override
    public boolean hasNext() {
        int lastIndex = values.length - 1;
        boolean result = false;
        if (values.length != 0
                && (rowIndex < lastIndex || (rowIndex == lastIndex && columnIndex <= values[lastIndex].length - 1))) {
            result = true;
        }
        return result;
    }

    /**
     * Retuns next element from two-dimensional array and moves carriage forward for one element.
     *
     * @return next element from two-dimensional array.
     * @throws NoSuchElementException - in case there is none elements left.
     */
    @Override
    public Object next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int result = values[rowIndex][columnIndex++];
        if (columnIndex >= values[rowIndex].length) {
            columnIndex = 0;
            rowIndex++;
        }
        return result;
    }
}
