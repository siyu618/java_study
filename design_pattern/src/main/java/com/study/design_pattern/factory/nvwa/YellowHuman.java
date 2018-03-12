package com.study.design_pattern.factory.nvwa;

/**
 *
 * @author tianyuzhi
 * @date 18/3/6
 */
public class YellowHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("My color is Yellow");
    }

    @Override
    public void talk() {
        System.out.println("I am yellow");
    }
}
