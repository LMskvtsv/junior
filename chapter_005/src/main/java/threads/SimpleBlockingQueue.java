package threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("lock")
    private Queue<T> queue = new LinkedList<>();
    private final Object lock = new Object();

    private final int maxSize = 5;

    public int getSize() {
        synchronized (lock) {
            return queue.size();
        }
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this.lock) {
            while (queue.size() >= maxSize) {
                System.out.printf("Queue is too full, size is %d, waiting when consumer take some of them out.%s", queue.size(), System.lineSeparator());
                this.lock.wait();
            }
            queue.offer(value);
            System.out.printf("%s - object added in queue%s", value.toString(), System.lineSeparator());
            this.lock.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        T t;
        synchronized (this.lock) {
            while (queue.size() <= 0) {
                System.out.println("Queue is empty, waiting for data.");
                this.lock.wait();
            }
            t = queue.poll();
            System.out.printf("%s - object was deleted from queue%s", t.toString(), System.lineSeparator());
            this.lock.notify();
        }
        return t;
    }
}
