package list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomLinkedList<E> implements Iterable<E> {

    private int size = 0;
    private int modCount = 0;
    private Node<E> last;

    /**
     * Метод добавления нового элемента в коллекцию.
     */
    public void add(E value) {
        Node<E> newNode = new Node(value);
        newNode.previous = last;
        last = newNode;
        this.size++;
    }

    /**
     * Метод получения значения по индексу. Идекс отсчитывается начиная с первого добавленного элемента.
     */
    public E get(int index) {
        Node<E> nodeToReturn = last;
        for (int i = 0; i < size - 1 - index; i++) {
            nodeToReturn = nodeToReturn.previous;
        }
        return nodeToReturn.value;
    }

    /**
     * Возвращает длину списка.
     *
     * @return
     */

    public int getSize() {
        return size;
    }

    public E deleteLastAddedElement() {
        CustomLinkedList.Node<E> nodeToDelete = this.last;
        E deletedDate;
        if (nodeToDelete != null) {
            deletedDate = nodeToDelete.value;
            this.last = nodeToDelete.previous;
            size--;
        } else {
            deletedDate = null;
        }
        return deletedDate;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int savedModCount = modCount;
            private Node<E> current = last;

            @Override
            public boolean hasNext() {
                boolean result = false;
                if (modCount == savedModCount) {
                    if (current.previous != null) {
                        result = true;
                    }
                }
                return result;
            }

            int counter = 0;

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (counter != 0) {
                    current = current.previous;
                }
                counter++;
                return current.value;
            }
        };
    }

    /**
     * Класс предназначен для хранения данных.
     */

    private static class Node<E> {
        private E value;
        private Node<E> previous;

        private Node(E date) {
            this.value = date;
        }
    }
}
