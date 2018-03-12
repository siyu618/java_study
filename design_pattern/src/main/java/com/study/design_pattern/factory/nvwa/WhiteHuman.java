package com.study.design_pattern.factory.nvwa;

/**
 *
 * @author tianyuzhi
 * @date 18/3/6
 */
public class WhiteHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("My color is white");
    }

    @Override
    public void talk() {
        System.out.println("I am white");
    }
}
