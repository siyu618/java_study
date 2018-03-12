package com.study.design_pattern.strategy.car;

/**
 * Created by tianyuzhi on 18/3/8.
 */
public class RidingDriveBehavior implements IDriveBehavior {
    @Override
    public void driver() {
        System.out.println("骑行");
    }
}
