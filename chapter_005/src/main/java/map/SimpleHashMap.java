package map;

import list.CustomLinkedList;

public class SimpleHashMap<K, V> {

    CustomLinkedList<Node>[] container = new CustomLinkedList[16];
    double threshold = 0.75;
    private int size = 0;

    /**
     * Метод для вставки данных. В случае возникновения коллизий данные не вставляются.
     *
     * @param key
     * @param value
     * @return
     */
    public boolean insert(K key, V value) {
        boolean wasInserted = false;
        if (size >= container.length * threshold) {
            resizeAndTransfer(container);
        }
        Node<K, V> node = new Node<>(key, value);
        int index = indexFor(key.hashCode(), container.length);
        if (container[index] == null) {
            container[index] = new CustomLinkedList<>();
            container[index].add(node);
            size++;
            wasInserted = true;
        } else {
            if (container[index].getSize() == 1
                    && container[index].get(0).key.hashCode() == key.hashCode()
                    && container[index].get(0).key.equals(key)) {
                container[index].deleteLastAddedElement();
                container[index].add(node);
            }
        }
        return wasInserted;
    }

    /**
     * Расширяет размер коллекции при определенном уровне наполненности.
     *
     * @param container
     */
    private void resizeAndTransfer(CustomLinkedList<Node>[] container) {
        CustomLinkedList<Node>[] newContainer = new CustomLinkedList[container.length * 2];
        for (int i = 0; i < container.length; i++) {
            CustomLinkedList<Node> listToTransfer = container[i];
            if (listToTransfer != null) {
                int newIndex = indexFor(listToTransfer.get(0).key.hashCode(), newContainer.length);
                newContainer[newIndex] = listToTransfer;
            }
        }
        this.container = newContainer;
    }

    public int getSize() {
        return size;
    }

    /**
     * Получение значения по ключу.
     *
     * @param key
     * @return
     */
    public V get(K key) {
        V result = null;
        int index = indexFor(key.hashCode(), container.length);
        if (container[index] != null) {
            result = (V) container[index].get(0).value;
        }
        return result;
    }

    /**
     * Удаление значения по ключу.
     *
     * @param key
     * @return
     */
    public boolean delete(K key) {
        boolean wasDeleted = false;
        int index = indexFor(key.hashCode(), container.length);
        if (container[index] != null) {
            container[index] = null;
            size--;
            wasDeleted = true;
        }
        return wasDeleted;
    }

    /**
     * Расчет индекса массива для вставки данных.
     *
     * @param keyHash
     * @param tableLength
     * @return
     */
    private int indexFor(int keyHash, int tableLength) {
        return keyHash & (tableLength - 1);
    }

    /**
     * Класс для представления связи ключ - значение.
     *
     * @param <K>
     * @param <V>
     */
    private class Node<K, V> {
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
