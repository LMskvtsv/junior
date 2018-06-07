package threads;


import org.junit.Test;

public class ThreadProblemsExample {
    public int counter = 0;

    @Test
    public void example() throws InterruptedException {
        for (int i = 0; i < 300; i++) {
            CounterThread ct = new CounterThread();
            ct.start();
        }
        Thread.sleep(5_000);

        System.out.println("Counter:" + counter);
    }


    class CounterThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 2000; i++) {
                counter++;
            }
        }
    }
}

