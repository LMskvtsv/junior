package tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class SimpleBinaryTree<E extends Comparable<E>> implements Iterable {

    private BinaryNode<E> root;

    /**
     * @param element element.
     * @return
     */
    public void add(E element) {
        if (root == null) {
            root = new BinaryNode<>(element);
        } else {
            addRecursive(root, element);
        }
    }

    /**
     * Добавление элемента в случае непустого дерева.
     * @param node
     * @param element
     */
    private void addRecursive(BinaryNode<E> node, E element) {
        BinaryNode<E> newNode = new BinaryNode<>(element);
        if (node.getValue().compareTo(element) > 0) {
            if (node.left != null) {
                addRecursive(node.left, element);
            } else {
                node.left = newNode;
            }
        } else {
            if (node.right != null) {
                addRecursive(node.right, element);
            } else {
                node.right = newNode;
            }
        }
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            Queue<BinaryNode<E>> data = new LinkedList<>();

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
                BinaryNode<E> el;
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    el = data.poll();
                    if (el.right != null) {
                        data.offer(el.right);
                    }
                    if (el.left != null) {
                        data.offer(el.left);
                    }
                }
                return el.getValue();
            }
        };
    }
}
