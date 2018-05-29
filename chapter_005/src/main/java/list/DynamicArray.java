package list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynamicArray<E> implements Iterable<E> {

    private E[] array = (E[]) new Object[3];
    int index = 0;
    Integer modCount = new Integer(0);


    /**
     * Метод вставляет в начало списка данные.
     */

    public void add(E value) {
        if (index < array.length) {
            array[index++] = value;
        } else {
            E[] newArray = (E[]) new Object[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
            array[index++] = value;
            modCount++;
        }
    }

    /**
     * Метод получения текщего размера.
     */

    public int getSize() {
        return index;
    }


    /**
     * Метод получения элемента по индексу.
     */

    public E get(int index) {
        return array[index];
    }

    @Override
    public Iterator iterator() {
        return new Iterator<E>() {
            int savedModCount = modCount;
            int counter = 0;

            @Override
            public boolean hasNext() throws ConcurrentModificationException {
                boolean result = false;
                if (modCount == savedModCount) {
                    if (index != 0 && counter < index) {
                        result = true;
                    }
                } else {
                    throw new ConcurrentModificationException();
                }
                return result;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[counter++];
            }
        };
    }
}


