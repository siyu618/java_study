package com.study.design_pattern.delegate;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class TheCEO implements Mishu {
    @Override
    public void kaihui() {
        System.out.println("The CEO 正在开会");
    }
}
