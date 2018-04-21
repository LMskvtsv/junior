package search;

import java.util.LinkedList;

public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Метод должен вставлять в нужную позицию элемент.
     * Позиция определяется по полю приоритет.
     * Для вставки использовать add(int index, E value)
     *
     * @param task задача
     */
    public void put(Task task) {
        int index = 0;
        if (tasks.size() == 0) {
            index = 0;
        } else {
            for (Task t : tasks) {
                if (task.getPriority() <= t.getPriority()) {
                    index = tasks.indexOf(t);
                    break;
                } else {
                    index = tasks.indexOf(t) + 1;
                }
            }
        }
        tasks.add(index, task);
    }

    public Task take() {
        return this.tasks.poll();
    }
}
