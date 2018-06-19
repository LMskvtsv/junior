package tasks;

import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {
    static class Friend {
        ReentrantLock lock = new ReentrantLock();
        private final String name;

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void bow(Friend bower) {
            lock.lock();
            System.out.println("Thread locked bow: " + Thread.currentThread().getName());
            System.out.format("%s: %s"
                            + "  has bowed to me!%n",
                    this.name, bower.getName());
            System.out.println("Thread before bowBack: " + Thread.currentThread().getName());
            bower.bowBack(this);
            System.out.println("Thread unlocking bow: " + Thread.currentThread().getName());
            lock.unlock();
            System.out.println("Thread unlocked bow: " + Thread.currentThread().getName());
        }

        public void bowBack(Friend bower) {
            lock.lock();
            System.out.println("Thread locked bowBack: " + Thread.currentThread().getName());
            System.out.format("%s: %s"
                            + " has bowed back to me!%n",
                    this.name, bower.getName());
            System.out.println("Thread unlocking bowBack: " + Thread.currentThread().getName());
            lock.unlock();
            System.out.println("Thread unlocked bowBack: " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        final Friend alphonse =
                new Friend("Alphonse");
        final Friend gaston =
                new Friend("Gaston");
        new Thread(new Runnable() {
            public void run() {
                alphonse.bow(gaston);
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                gaston.bow(alphonse);
            }
        }).start();
    }
}
