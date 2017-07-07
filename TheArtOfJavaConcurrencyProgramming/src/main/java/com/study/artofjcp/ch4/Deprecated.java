package com.study.artofjcp.ch4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/7/7.
 */
public class Deprecated {
    public static void main(String[] args) throws InterruptedException {
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        Thread printThread = new Thread(() -> {
            while (true) {
                DateFormat format2 = new SimpleDateFormat("HH:mm:ss");
                System.out.println(Thread.currentThread().getName() + " run at " + format2.format(new Date()));
                SleepUtils.sleep(1);
            }
        }, "PrintThread");
        printThread.setDaemon(true);
        printThread.start();
        TimeUnit.SECONDS.sleep(3);

        printThread.suspend();
        System.out.println(Thread.currentThread().getName() + " suspend PrintThread at " + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);

        printThread.resume();
        System.out.println(Thread.currentThread().getName() + " resume PrintThread at " + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);

        printThread.stop();
        System.out.println(Thread.currentThread().getName() + " stop PrintThread at " + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);


    }
}
