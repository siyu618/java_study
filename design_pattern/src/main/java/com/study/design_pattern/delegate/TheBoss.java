package com.study.design_pattern.delegate;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class TheBoss implements Mishu {
    @Override
    public void kaihui() {
        System.out.println("The boss 亲自在开会");
    }
}
