package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс SimpleLinkedList.
 */

public class SimpleLinkedList<E> implements Iterable {


    private int size;

    private Node<E> last;

    /**
     * Метод вставляет в начало списка данные.
     */

    public void add(E date) {
        Node<E> newLink = new Node<>(date);
        newLink.previous = this.last;
        this.last = newLink;
        this.size++;
    }


    /**
     * Метод удаляет первый добавленный элемент в список.
     */

    public E deleteFirstAddedElement() {
        Node<E> nodeToDelete = this.last;
        E deletedDate;
        if (nodeToDelete.previous != null) {
            for (int i = 0; i < size - 2; i++) {
                nodeToDelete = nodeToDelete.previous;
            }
            deletedDate = nodeToDelete.previous.value;
            nodeToDelete.previous = null;
            size--;
        } else {
            deletedDate = nodeToDelete != null ? nodeToDelete.value : null;
        }
        return deletedDate;
    }

    /**
     * Метод проверяет содержится ли элемент в коллекции.
     */
    public boolean contains(E e) {
        boolean isExists = false;
        Node nodeToCompare = last;
        for (int i = 0; i < size; i++) {
            if (nodeToCompare.value.equals(e)) {
                isExists = true;
                break;
            }
            nodeToCompare = nodeToCompare.previous;
        }
        return isExists;
    }

    /**
     * Метод удаляет последний добавленный элемент в списке.
     */

    public E deleteLastAddedElement() {
        Node<E> nodeToDelete = this.last;
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

    /**
     * Метод получения элемента по индексу.
     */

    public E get(int index) {
        Node<E> result = this.last;
        for (int i = 0; i < index; i++) {
            result = result.previous;
        }
        return result.value;
    }

    /**
     * Метод получения размера коллекции.
     */

    public int getSize() {
        return this.size;
    }

    /**
     * Класс предназначен для хранения данных.
     */

    private static class Node<E> {
        E value;
        Node<E> previous;

        Node(E date) {
            this.value = date;
        }
    }

    @Override
    public Iterator iterator() {
        return new Iterator<E>() {
            Node node = last;

            @Override
            public boolean hasNext() {
                return node != null && node.previous != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Node nextNode = node.previous;
                node = nextNode;
                return (E) nextNode.value;
            }
        };
    }

}
