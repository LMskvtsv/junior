package threads;

import org.junit.Test;

public class NonBlockingCacheTest {
    @Test
    public void when2ThreadsUpdateThenException() {
        NonBlockingCache cache = new NonBlockingCache();

        cache.add(new Base(1, "first"));
        cache.add(new Base(2, "second"));
        cache.add(new Base(3, "third"));
        cache.add(new Base(4, "fourth"));
        cache.add(new Base(5, "fifth"));

        Thread first = new Thread() {
            @Override
            public void run() {
                cache.update(new Base(1, "first1"));
                cache.update(new Base(2, "second1"));
                cache.update(new Base(3, "third1"));
                cache.update(new Base(4, "fourth1"));
                cache.update(new Base(5, "fifth1"));
            }
        };

        Thread second = new Thread() {
            @Override
            public void run() {
                cache.update(new Base(1, "first2"));
                cache.update(new Base(2, "second2"));
                cache.update(new Base(3, "third2"));
                cache.update(new Base(4, "fourth2"));
                cache.update(new Base(5, "fifth2"));
            }
        };

        Thread third = new Thread() {
            @Override
            public void run() {
                cache.update(new Base(1, "first3"));
                cache.update(new Base(2, "second3"));
                cache.update(new Base(3, "third3"));
                cache.update(new Base(4, "fourth3"));
                cache.update(new Base(5, "fifth3"));
            }
        };

        first.start();
        second.start();
        third.start();
        try {
            first.join();
            second.join();
            third.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache);
    }
}