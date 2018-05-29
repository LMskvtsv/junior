package list;

public class SimpleQueue<T> {

    private SimpleLinkedList<T> list = new SimpleLinkedList<>();

    public T poll() {
        return list.deleteFirstAddedElement();
    }

    public void push(T value) {
        list.add(value);
    }
}

