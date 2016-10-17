package com.study.concurrenty;

/**
 * Created by tianyuzhi on 16/10/13.
 */
public class TwoSumsCriticalSection {
    private int sum1 = 0;
    private int sum2 = 0;

    private Integer sum1Lock = new Integer(1);
    private Integer sum2Lock = new Integer(2);

//    public void add(int val1, int val2) {
//        synchronized (this) {
//            sum1 += val1;
//            sum2 += val2;
//        }
//    }


    public void add(int val1, int val2) {
        synchronized (sum1Lock) {
            sum1 += val1;
        }
        synchronized (sum2Lock) {
            sum2 += val2;
        }
    }
}
