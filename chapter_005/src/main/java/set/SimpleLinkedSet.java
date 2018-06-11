package set;

import list.SimpleLinkedList;
import java.util.Iterator;


public class SimpleLinkedSet<E> implements Iterable {

    SimpleLinkedList data = new SimpleLinkedList();

    /**
     * Метод вставляет данные, только в случае если нет идентичного элемента.
     */
    public boolean add(E e) {
        boolean wasAdded = false;
        if (!contains(e)) {
            data.add(e);
            wasAdded = true;
        }
        return wasAdded;
    }

    /**
     * Метод проверяет содержится ли элемент в коллекции.
     */
    public boolean contains(E e) {
        return data.contains(e);
    }

    /**
     * Получение размера коллекции.
     */
    public int getSize() {
        return data.getSize();
    }


    @Override
    public Iterator iterator() {
        return data.iterator();
    }
}
