package tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Queue;

public class SimpleTree<E extends Comparable<E>> implements Iterable {

    public SimpleTree(E root) {
        this.root = new Node(root);
    }

    private Node<E> root;

    /**
     * Добавить элемент child в parent.
     * Parent может иметь список child.
     *
     * @param parent parent.
     * @param child  child.
     * @return
     */
    public boolean add(E parent, E child) {
        Optional<Node<E>> rsl = findBy(parent);
        if (!rsl.isPresent()) {
            this.root.leaves().add(new Node(parent));
        }
        return rsl.get().leaves().add(new Node(child));
    }

    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            Queue<Node<E>> data = new LinkedList<>();

            {
                data.offer(root);
            }

            @Override
            public boolean hasNext() {
                boolean result = false;
                if (!data.isEmpty()) {
                    result = true;
                }
                return result;
            }

            @Override
            public E next() {
                Node<E> el;
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    el = data.poll();
                    for (Node<E> child : el.leaves()) {
                        data.offer(child);
                    }
                }
                return el.getValue();
            }
        };
    }
}
