package list;

public class SimpleStack<T> {

    private SimpleLinkedList<T> list = new SimpleLinkedList<>();

    public T poll() {
        return list.deleteLastAddedElement();
    }

    public void push(T value) {
        list.add(value);
    }
}

