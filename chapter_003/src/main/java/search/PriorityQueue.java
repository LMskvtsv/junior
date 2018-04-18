package search;

import java.util.LinkedList;

public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Метод должен вставлять в нужную позицию элемент.
     * Позиция определять по полю приоритет.
     * Для вставик использовать add(int index, E value)
     *
     * @param task задача
     */
    public void put(Task task) {
        tasks.add(task);
        tasks.sort((task1, task2) -> (task1.getPriority() - task2.getPriority()));
    }

    public Task take() {
        return this.tasks.poll();
    }
}
