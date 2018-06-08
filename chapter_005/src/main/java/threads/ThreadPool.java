package threads;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    final Object lock = new Object();

    public ThreadPool(int nThreads) {
        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (!this.isInterrupted()) {
                        Runnable nextTask = tasks.poll();
                        if (nextTask != null) {
                            nextTask.run();
                        } else {
                            synchronized (lock) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    return;
                                }
                            }
                        }
                    }
                }
            };
            t.start();
            threads.add(t);
            System.out.printf("Thread %s was added and started.%s", t.getName(), System.lineSeparator());
        }
    }

    private final Queue<Runnable> tasks = new LinkedBlockingQueue<>();


    public void work(Runnable job) {
        tasks.offer(job);
        synchronized (lock) {
            lock.notifyAll();
        }

    }

    public void shutdown() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }

}
