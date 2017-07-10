package com.study.artofjcp.ch6;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by tianyuzhi on 17/7/10.
 */
public class BadUsingHashMap {

    public static void main(String[] args) throws InterruptedException {
        final HashMap<String, String> map = new HashMap<>(2);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i ++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map.put(UUID.randomUUID().toString(), "");
                        }
                    }, "ftf"+i).start();
                }
            }
        });
        thread.start();
        thread.join();

    }
}
