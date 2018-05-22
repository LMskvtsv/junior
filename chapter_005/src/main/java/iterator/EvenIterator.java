package iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenIterator implements Iterator {

    private int[] values;
    private int index = 0;

    public EvenIterator(int[] values) {
        this.values = values;
    }

    /**
     * Checks if array has next even value.
     *
     * @return false in case array is empty, or current value is last even value in array.
     */
    @Override
    public boolean hasNext() {
        boolean result = false;
        for (int i = index; i < values.length; i++) {
            if (values[i] % 2 == 0) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Retuns next even element from  array and moves carriage forward for one or several elements.
     *
     * @return next even element from  array.
     * @throws java.util.NoSuchElementException - in case there is none elements left.
     */
    @Override
    public Object next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int result = -1;
        for (int i = index; i < values.length; i++) {
            if (values[i] % 2 == 0) {
                result = values[i];
                index++;
                break;
            }
            index++;
        }
        return result;
    }
}
