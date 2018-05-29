package list;

/**
 * Класс SimpleLinkedList.
 */

public class SimpleLinkedList<E> {


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
            deletedDate = nodeToDelete.previous.date;
            nodeToDelete.previous = null;
            size--;
        } else {
            deletedDate = nodeToDelete != null ? nodeToDelete.date : null;
        }
        return deletedDate;
    }

    /**
     * Метод удаляет последний добавленный элемент в списке.
     */

    public E deleteLastAddedElement() {
        Node<E> nodeToDelete = this.last;
        E deletedDate;
        if (nodeToDelete != null) {
            deletedDate = nodeToDelete.date;
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
        return result.date;
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
        E date;
        Node<E> previous;

        Node(E date) {
            this.date = date;
        }
    }
}
