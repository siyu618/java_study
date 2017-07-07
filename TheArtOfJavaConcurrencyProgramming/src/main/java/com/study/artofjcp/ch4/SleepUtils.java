package com.study.artofjcp.ch4;

import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/7/6.
 */
public class SleepUtils {
    public static final void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
