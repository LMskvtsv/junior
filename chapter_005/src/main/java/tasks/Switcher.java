package tasks;

import java.util.concurrent.locks.ReentrantLock;

public class Switcher {
    public static void main(String[] args) {
        Person person = new Person();
        ReentrantLock lock = new ReentrantLock(true);
        person.appendName(4);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        while (!lock.tryLock()) {
                            //waiting
                        }
                        for (int i = 0; i < 10; i++) {
                            System.out.println(person.appendName(1));
                            Thread.currentThread().sleep(1_000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                        try {
                            Thread.currentThread().sleep(1_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {

                        while (!lock.tryLock()) {
                            //waiting
                        }
                        for (int i = 0; i < 10; i++) {
                            System.out.println(person.appendName(2));
                            Thread.currentThread().sleep(1_000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                        try {
                            Thread.currentThread().sleep(1_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
