package com.study.artofjcp.ch8;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by tianyuzhi on 17/7/10.
 */
public class CyclicBarrierTest2 {
    static CyclicBarrier c = new CyclicBarrier(2, new Thread(() ->{
        System.out.println(3);
    }));

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                c.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(1);
        }).start();
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(2);
    }


}
