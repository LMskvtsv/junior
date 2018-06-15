package tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Queue;

public class SimpleTree<E extends Comparable<E>> implements Iterable {

    public SimpleTree(E root) {
        this.root = new Node(root);
    }

    private Node<E> root;

    /**
     * Определяет является ли дерево двоичным.
     *
     * @return true если дерево двоичное.
     */
    public boolean isBinary() {
        Iterator iterator = this.iterator();
        boolean result = true;
        while (iterator.hasNext()) {
            if (findBy((E) iterator.next()).get().leaves().size() > 2) {
                result = false;
                break;
            }
        }
        return result;
    }

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
        List<Node<E>> list = rsl.get().leaves();
        boolean hasSuchElement = false;
        for (Node node : list) {
            if (node.eqValue(child)) {
                hasSuchElement = true;
                break;
            }
        }
        if (!hasSuchElement) {
            list.add(new Node(child));
        }
        return !hasSuchElement;
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
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Queue<Node<E>> data = new LinkedList<>();

            {
                data.offer(root);
            }

            @Override
            public boolean hasNext() {
                return !data.isEmpty();
            }

            @Override
            public E next() {
                Node<E> el;
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    el = data.poll();
                    data.addAll(el.leaves());
                }
                return el.getValue();
            }
        };
    }
}
