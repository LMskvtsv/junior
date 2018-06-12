package threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.junit.Test;

@ThreadSafe
public class ThreadPoolTest {
    @GuardedBy("this")
    private int counter = 0;

    @Test
    public void threadPoolTest() {
        int size = Runtime.getRuntime().availableProcessors();
        ThreadPool pool = new ThreadPool(size);
        pool.initAndStartAllThreads();
        Runnable r = new Runnable() {
            @Override
            public synchronized void run() {
                System.out.println(++counter + " current thread " + Thread.currentThread().getName());
            }
        };
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.work(r);
        pool.shutdown();
    }
}