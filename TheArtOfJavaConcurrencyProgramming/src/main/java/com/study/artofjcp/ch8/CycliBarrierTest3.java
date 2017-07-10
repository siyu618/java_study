package com.study.artofjcp.ch8;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by tianyuzhi on 17/7/10.
 */
public class CycliBarrierTest3 {
    static CyclicBarrier c = new CyclicBarrier(2);
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                c.await();
            } catch (Exception e){
            }
        });
        thread.start();
        thread.interrupt();
        try {
            c.await();
        } catch (Exception e) {
            System.out.println(c.isBroken());
        }

    }
}
