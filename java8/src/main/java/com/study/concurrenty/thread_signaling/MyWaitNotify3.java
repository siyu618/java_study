package com.study.concurrenty.thread_signaling;

/**
 * Created by tianyuzhi on 16/10/13.
 */
public class MyWaitNotify3 {

    public static class MonitorObject {
    }

    private MonitorObject monitorObject = new MonitorObject();
    private boolean wasSignalled = false; // to store the signal..

    public void doWait() {
        synchronized (monitorObject) {
            while (!wasSignalled) { // use while here : for spurious wakeup
                try {
                    System.out.println(Thread.currentThread().getName() + " starts to wait");
                    System.out.println("do wait");
                    monitorObject.wait(); // IMPORTANT: it will release the lock
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            wasSignalled = false;
        }
    }

    public void doNotify() {
        synchronized (monitorObject) {
            System.out.println(Thread.currentThread().getName() + " starts to notify");
            wasSignalled = true;
            monitorObject.notify();
            System.out.println("do notify");
        }
    }

    /**
     * notify() & wait() should be called in a synchronized block.
     * <p>
     * As you can see both the waiting and notifying thread calls wait() and notify() from within a synchronized block.
     * This is mandatory!
     * A thread cannot call wait(), notify() or notifyAll() without holding the lock on the object the method is called on.
     * If it does, an IllegalMonitorStateException is thrown.
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        MyWaitNotify3 myWaitNotify = new MyWaitNotify3();
        Thread t1 = new Thread(() -> {
            myWaitNotify.doWait();
        }, "t1");
        Thread t2 = new Thread(() -> {
            myWaitNotify.doNotify();
        }, "t2");

//        t2.start();
//       // Thread.sleep(1000);
//        t1.start();
        /**
         * if notify() happens before wait(), it will be always waiting...
         * So the "Missed signals"
         */
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
