package com.study.design_pattern.strategy.car;

/**
 * Created by tianyuzhi on 18/3/8.
 */
public class NoDriveBehavior implements IDriveBehavior {
    @Override
    public void driver() {
        System.out.println("没有驾驶和骑行功能");
    }
}
