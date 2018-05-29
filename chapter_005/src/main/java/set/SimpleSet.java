package set;


import list.DynamicArray;

import java.util.Iterator;

public class SimpleSet<E> implements Iterable {

    DynamicArray<E> container = new DynamicArray<>();

    /**
     * Метод вставляет данные, только в случае если нет идентичного элемента.
     */
    public void add(E e) {
        if (!contains(e)) {
            container.add(e);
        }
    }

    /**
     * Получение размера коллекции.
     */
    public int getSize() {
        return container.getSize();
    }

    /**
     * Метод проверяет содержится ли элемент в коллекции.
     */
    public boolean contains(E e) {
        boolean isExists = false;
        for (E element : container) {
            if (e.equals(element)) {
                isExists = true;
                break;
            }
        }
        return isExists;
    }

    @Override
    public Iterator iterator() {
        return container.iterator();
    }
}
