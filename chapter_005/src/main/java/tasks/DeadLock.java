package tasks;

import java.util.concurrent.CountDownLatch;

public class DeadLock extends Thread {

    private final CountDownLatch latch;
    private final Object obj1;
    private final Object obj2;

    DeadLock(Object obj1, Object obj2, CountDownLatch latch) {
        this.obj1 = obj1;
        this.obj2 = obj2;
        this.latch = latch;
    }

    @Override
    public void run() {
        synchronized (obj1) {
            System.out.println("Object " + obj1 + " locked by thread " + Thread.currentThread().getName());
            latch.countDown();
            System.out.println("countdown -1");
            try {
                System.out.println("Thread is waiting, obj " + obj1 + " still locked by thread " + Thread.currentThread().getName());
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
            System.out.println("Thread finished waiting " + Thread.currentThread().getName());
            System.out.println("Before sync 2 section " + Thread.currentThread().getName());
            synchronized (obj2) {
                System.out.println("Object " + obj2 + " locked by thread " + Thread.currentThread().getName());
                System.out.println("Thread finished");
            }
        }
    }

    public static void main(String[] args) {
        final Object obj1 = new Object();
        System.out.println("object1 " + obj1);
        final Object obj2 = new Object();
        System.out.println("object2 " + obj2);
        final CountDownLatch latch = new CountDownLatch(2);
        new DeadLock(obj1, obj2, latch).start();
        new DeadLock(obj2, obj1, latch).start();
    }
}
