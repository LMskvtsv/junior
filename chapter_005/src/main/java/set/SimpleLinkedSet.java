package set;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleLinkedSet<E> implements Iterable {

    int index = 0;
    Node last;

    /**
     * Метод вставляет данные, только в случае если нет идентичного элемента.
     */
    public boolean add(E e) {
        boolean wasAdded = false;
        if (!contains(e)) {
            Node newNode = new Node(e);
            newNode.previous = last;
            last = newNode;
            index++;
            wasAdded = true;
        }
        return wasAdded;
    }

    /**
     * Получение размера коллекции.
     */
    public int getSize() {
        return index;
    }

    /**
     * Метод проверяет содержится ли элемент в коллекции.
     */
    public boolean contains(E e) {
        boolean isExists = false;
        Node nodeToCompare = last;
        for (int i = 0; i < index; i++) {
            if (nodeToCompare.value.equals(e)) {
                isExists = true;
                break;
            }
            nodeToCompare = nodeToCompare.previous;
        }
        return isExists;
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

    private class Node<E> {
        E value;
        Node previous;

        public Node(E value) {
            this.value = value;
        }
    }
}
