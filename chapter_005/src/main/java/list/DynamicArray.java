package list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.NoSuchElementException;

@ThreadSafe
public class DynamicArray<E> implements Iterable<E> {
    @GuardedBy("this")
    private  E[] array = (E[]) new Object[3];
    @GuardedBy("this")
    private int index = 0;
    @GuardedBy("this")
    private Integer modCount = 0;


    /**
     * Метод вставляет в начало списка данные.
     */

    public synchronized void add(E value) {
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

    public synchronized int getSize() {
        return index;
    }


    /**
     * Метод получения элемента по индексу.
     */

    public synchronized E get(int index) {
        return array[index];
    }

    @Override
    public Iterator iterator() {
        return new Iterator<E>() {
            int savedModCount = modCount;
            int counter = 0;

            @Override
            public  boolean hasNext() {
                boolean result = false;
                if (modCount == savedModCount) {
                    if (index != 0 && counter < index) {
                        result = true;
                    }
                }
                return result;
            }

            @Override
            public  E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[counter++];
            }
        };
    }
}


