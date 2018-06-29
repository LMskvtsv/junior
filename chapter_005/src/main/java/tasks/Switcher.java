package tasks;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Switcher {
    public static void main(String[] args) {
        Person person = new Person();
        CyclicBarrier barrier = new CyclicBarrier(2);
        person.appendName(4);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println(person.appendName(1));
                        try {
                            Thread.currentThread().sleep(1_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        System.out.println("Thread waiting " + Thread.currentThread().getName());
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    barrier.reset();

                    System.out.println("Reset barrier");
                    try {
                        System.out.println("Thread waiting " + Thread.currentThread().getName());
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Thread finished wait " + Thread.currentThread().getName());

                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        System.out.println("Thread waiting " + Thread.currentThread().getName());
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Thread finished wait " + Thread.currentThread().getName());
                    for (int i = 0; i < 10; i++) {
                        System.out.println(person.appendName(2));
                        try {
                            Thread.currentThread().sleep(1_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        System.out.println("Thread waiting " + Thread.currentThread().getName());
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        thread1.start();
        thread2.start();
    }
}
