package com.study.artofjcp.ch8;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tianyuzhi on 17/7/10.
 */
public class ExchangerTest {
    private static final Exchanger<String> exchanger = new Exchanger<>();
    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        executor.execute(()-> {
          String A  = "A";
            try {
                exchanger.exchange(A);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        executor.execute(()-> {
            String B  = "B";
            try {
                String A = exchanger.exchange("B");
                System.out.println("A B the same ?" + A.equals(B) + ", A:" + A + ",B:" + B);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
    }

}
