package com.study.artofjcp.ch4;

/**
 * Created by tianyuzhi on 17/7/6.
 */
public class Daemon {

    public static void main(String[] args) {
        Thread thread = new Thread(new DamonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }

    static class DamonRunner implements Runnable {

        @Override
        public void run() {
            try {
                SleepUtils.sleep(10);
            } finally {
                System.out.println("Daemon Thread finally run.");
            }
        }
    }
}
