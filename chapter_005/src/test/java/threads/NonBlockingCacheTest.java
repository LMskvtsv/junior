package threads;

import org.junit.Test;

public class NonBlockingCacheTest {

    @Test
    public void when2ThreadsUpdateThenException() {
       /* NonBlockingCache cache = new NonBlockingCache();

        Base base1 = new Base(1, "first");
        Base base2 = new Base(1, "first");
        Base base3 = new Base(2, "second");
        Base base4 = new Base(3, "third");
        Base base5 = new Base(4, "fourth");
        Base base6 = new Base(5, "fifth");

        cache.add(base1);
        cache.add(base2);
        cache.add(base3);
        cache.add(base4);
        cache.add(base5);
        cache.add(base6);


        Thread first = new Thread() {
            @Override
            public void run() {
                base1.setInfo("first1");
                base2.setInfo("first1");
                base3.setInfo("second1");
                base4.setInfo("third1");
                base5.setInfo("fourth1");
                base6.setInfo("fifth1");
                cache.update(base1);
                cache.update(base2);
                cache.update(base3);
                cache.update(base4);
                cache.update(base5);
            }
        };

        Thread second = new Thread() {
            @Override
            public void run() {
                base1.setInfo("first2");
                base2.setInfo("first2");
                base3.setInfo("second2");
                base4.setInfo("third2");
                base5.setInfo("fourth2");
                base6.setInfo("fifth2");
                cache.update(base1);
                cache.update(base2);
                cache.update(base3);
                cache.update(base4);
                cache.update(base5);
            }
        };

        Thread third = new Thread() {
            @Override
            public void run() {
                base1.setInfo("first3");
                base2.setInfo("first3");
                base3.setInfo("second3");
                base4.setInfo("third3");
                base5.setInfo("fourth3");
                base6.setInfo("fifth3");
                cache.update(base1);
                cache.update(base2);
                cache.update(base3);
                cache.update(base4);
                cache.update(base5);
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
        System.out.println(cache);*/
    }
}