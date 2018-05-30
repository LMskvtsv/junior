package set;

public class SimpleHashSet<E> {

    private Cell[] array = new Cell[3];
    private int index = 0;

    /**
     * Adds element into collection if there is no such element.
     */
    public boolean add(E e) {
        boolean result = false;
        if (!contains(e)) {
            if (index < array.length) {
                array[index++] = new Cell(e);
            } else {
                Cell[] newArray = new Cell[array.length * 2];
                System.arraycopy(array, 0, newArray, 0, array.length);
                array = newArray;
                array[index++] = new Cell(e);
            }
            result = true;
        }
        return result;
    }

    /**
     * Getting the size of collection.
     */
    public int getSize() {
        return index;
    }

    /**
     * Checks if element exists in collection.
     *
     * @param newElement
     * @return
     */
    public boolean contains(E newElement) {
        boolean contains = false;
        int newElementHashCode = newElement.hashCode();
        for (Cell element : array) {
            if (element != null && element.value == newElementHashCode) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    /**
     * Removes element from collection
     *
     * @param e element to remove.
     * @return true in case element was found and deleted.
     */
    public boolean remove(E e) {
        boolean wasDeleted = false;
        Cell[] newArray = new Cell[array.length];
        int toDeleteElementHashCode = e.hashCode();
        int newIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && toDeleteElementHashCode != array[i].value) {
                newArray[newIndex++] = array[i];
            } else {
                wasDeleted = true;
            }
        }
        index = newIndex;
        array = newArray;
        return wasDeleted;
    }

    /**
     * Class with key and value to create hash table.
     *
     * @param <E>
     */
    private class Cell<E> {
        E key;
        int value;

        public Cell(E key) {
            this.key = key;
            this.value = key.hashCode();
        }
    }
}
