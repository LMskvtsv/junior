package generic;

import java.util.Arrays;
import java.util.Iterator;

public class SimpleArray<T> implements Iterable<T> {

    T[] array;
    int index = 0;

    public SimpleArray(int size) {
        this.array = (T[]) new Object[size];
    }

    /**
     * Adds element into array.
     *
     * @param model
     * @throws ArrayIndexOutOfBoundsException in case there is no any space left.
     */
    public void add(T model) throws ArrayIndexOutOfBoundsException {
        if (index >= array.length - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        array[index++] = model;
    }

    /**
     * Replace or set new value by index.
     *
     * @param index
     * @param model value
     */
    public void set(int index, T model) {
        if (index < array.length - 1) {
            array[index] = model;
        }
    }

    /**
     * Deletes element. Copy array into new one except one - that needs to be deleted.
     *
     * @param index
     */
    public void delete(int index) {
        T[] newArray = (T[]) new Object[array.length];
        int newIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (i != index) {
                newArray[newIndex++] = array[i];
            }
        }
        array = newArray;
    }

    /**
     * Get element by index.
     *
     * @param index - index
     * @return element by index
     */
    public T getValue(int index) {
        return array[index];
    }

    /**
     * Get first index by element value.
     *
     * @param value
     * @return index of the element.
     */
    public int getIndex(T value) {
        int k = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                k = i;
                break;
            }
        }
        return k;
    }

    @Override
    public Iterator<T> iterator() {
        return Arrays.asList(array).iterator();
    }
}


