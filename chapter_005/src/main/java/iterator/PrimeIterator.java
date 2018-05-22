package iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrimeIterator implements Iterator {

    private int[] values;
    private int index = 0;

    public PrimeIterator(int[] values) {
        this.values = values;
    }

    /**
     * Checks if array has next prime value.
     *
     * @return false in case array is empty, or current value is last prime value in array.
     */
    @Override
    public boolean hasNext() {
        boolean result = false;
        for (int i = index; i < values.length; i++) {
            if (isPrimeNumber(values[i])) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean isPrimeNumber(int number) {
        boolean result = true;
        int previous = 2;
        if (number <= 1) {
            result = false;
        } else if (number > 2) {
            while (previous < number) {
                if (number % previous == 0) {
                    result = false;
                    break;
                }
                previous++;
            }
        }
        return result;
    }

    /**
     * Retuns next prime element from  array and moves carriage forward for one or several elements.
     *
     * @return next prime element from  array.
     * @throws java.util.NoSuchElementException - in case there is none elements left.
     */
    @Override
    public Object next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int result = -1;
        for (int i = index; i < values.length; i++) {
            if (isPrimeNumber(values[i])) {
                result = values[i];
                index++;
                break;
            }
            index++;
        }
        return result;
    }
}
