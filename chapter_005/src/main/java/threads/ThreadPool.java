package threads;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private final LinkedBlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final int numberOfThreads;

    public ThreadPool(int nThreads) {
        numberOfThreads = nThreads;
    }

    public void initAndStartAllThreads() {
        for (int i = 0; i < numberOfThreads; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (!this.isInterrupted()) {
                        Runnable nextTask;
                        try {
                            nextTask = tasks.poll(1_000, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException e) {
                            System.out.printf("Thread %s was interrupted.%s", Thread.currentThread().getName(), System.lineSeparator());
                            return;
                        }
                        if (nextTask != null) {
                            nextTask.run();
                        }
                    }
                }
            };
            t.start();
            threads.add(t);
            System.out.printf("Thread %s was added and started.%s", t.getName(), System.lineSeparator());
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job, 1_000, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        for (Thread t: threads) {
            t.interrupt();
        }
    }
}
