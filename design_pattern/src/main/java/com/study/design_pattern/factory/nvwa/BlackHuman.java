package com.study.design_pattern.factory.nvwa;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class BlackHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("My color is Black");
    }

    @Override
    public void talk() {
        System.out.println("I am black");
    }
}
