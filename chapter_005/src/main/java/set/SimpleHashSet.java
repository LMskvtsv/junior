package set;

import map.SimpleHashMap;

public class SimpleHashSet<E> {

    private SimpleHashMap<E, Object> map = new SimpleHashMap<>();

    /**
     * Adds element into collection if there is no such element.
     */
    public boolean add(E e) {
        return map.insert(e, new Object());
    }

    /**
     * Getting the size of collection.
     */
    public int getSize() {
        return map.getSize();
    }

    /**
     * Checks if element exists in collection.
     *
     * @param newElement
     * @return
     */
    public boolean contains(E newElement) {
        boolean contains = false;
        if (map.get(newElement) != null) {
            contains = true;
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
        return map.delete(e);
    }
}
