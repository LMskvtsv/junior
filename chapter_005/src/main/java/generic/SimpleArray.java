package generic;

import java.util.Arrays;
import java.util.Iterator;

public class SimpleArray<T> implements Iterable<T> {

    T[] array;
    int index = 0;

    public SimpleArray(int size) {
        this.array = (T[]) new Object[size];
    }

    public void add(T model) throws ArrayIndexOutOfBoundsException {
        if (index >= array.length - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        array[index++] = model;
    }

    public void set(int index, T model) {
        if (index < array.length - 1) {
            array[index] = model;
        }
    }

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

    public T get(int index) throws ArrayIndexOutOfBoundsException {
        return array[index];
    }

    @Override
    public Iterator<T> iterator() {
        return Arrays.asList(array).iterator();
    }
}


