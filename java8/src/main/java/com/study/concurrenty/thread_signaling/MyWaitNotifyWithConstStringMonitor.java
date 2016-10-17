package com.study.concurrenty.thread_signaling;

/**
 * Created by tianyuzhi on 16/10/13.
 */
public class MyWaitNotifyWithConstStringMonitor {
    String myMonitorObject = "";
    //  multiple instant of  MyWaitNotifyWithConstStringMonitor will share the same monitor object
    // and this will cause issues, just like send monitor1's signal to monitor2

    boolean wasSignalled = false;

    public void doWait(){
        synchronized(myMonitorObject){
            while(!wasSignalled){
                try{
                    myMonitorObject.wait();
                } catch(InterruptedException e){

                }
            }
            //clear signal and continue running.
            wasSignalled = false;
        }
    }

    public void doNotify(){
        synchronized(myMonitorObject){
            wasSignalled = true;
            myMonitorObject.notify();
        }
    }
}
