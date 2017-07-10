package com.study.artofjcp.ch8;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by tianyuzhi on 17/7/10.
 */
public class BankWaterService extends Thread {
    private CyclicBarrier c = new CyclicBarrier(4, this);

    private Executor executor = Executors.newFixedThreadPool(4);
    private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();
    private void count() {
        for (int i = 0; i < 4; i ++) {
            executor.execute(() -> {
                sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void run() {
        int result = 0;
        for (Map.Entry<String, Integer> entry : sheetBankWaterCount.entrySet()) {
            result += entry.getValue();
        }
        sheetBankWaterCount.put("result", result);
        System.out.println(sheetBankWaterCount);
    }

    public static void main(String[] args) {
        BankWaterService bankWaterService = new BankWaterService();
        bankWaterService.count();
    }
}
