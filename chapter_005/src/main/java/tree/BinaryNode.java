package tree;

public class BinaryNode<E extends Comparable<E>> {
    private final E value;
    BinaryNode<E> left;
    BinaryNode<E> right;

    public BinaryNode(final E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

}
