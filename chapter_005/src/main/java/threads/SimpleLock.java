package threads;

public class SimpleLock {

    private boolean isLocked = false;
    private Thread currentThread;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
        currentThread = Thread.currentThread();
    }

    public synchronized void unlock() {
        if (currentThread != null && Thread.currentThread() == currentThread) {
            isLocked = false;
            notifyAll();
        } else {
            System.out.printf("Thread %s cannot unlock, because it was locked by thread %s", Thread.currentThread(), currentThread);
        }
    }
}
