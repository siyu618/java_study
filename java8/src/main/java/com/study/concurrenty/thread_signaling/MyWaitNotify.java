package com.study.concurrenty.thread_signaling;

/**
 * Created by tianyuzhi on 16/10/13.
 */
public class MyWaitNotify {

    public static class MonitorObject {}

    private MonitorObject monitorObject = new MonitorObject();
    // important here:  monitorObject should be instant owned.

    private MonitorObject monitorObject2 = new MonitorObject();

    public void doWait() {
        synchronized (monitorObject) {
            try {
             //   System.out.println(Thread.currentThread().getName() + " starts to wait");
                monitorObject.wait(); // IMPORTANT: it will release the lock
                System.out.println("do wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void doNotify() {
        synchronized (monitorObject) {
           // System.out.println(Thread.currentThread().getName() + " starts to notify");
            monitorObject.notify();
            System.out.println("do notify");
        }
    }

    /**
     * notify() & wait() should be called in a synchronized block.
     *
     * As you can see both the waiting and notifying thread calls wait() and notify() from within a synchronized block.
     * This is mandatory!
     * A thread cannot call wait(), notify() or notifyAll() without holding the lock on the object the method is called on.
     * If it does, an IllegalMonitorStateException is thrown.
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        MyWaitNotify myWaitNotify = new MyWaitNotify();
        Thread t1 = new Thread(()->{
            myWaitNotify.doWait();
        }, "t1");
        Thread t2 = new Thread(()->{
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
