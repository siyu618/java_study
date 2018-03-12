package com.study.design_pattern.strategy.zhaoyun;

/**
 * Created by tianyuzhi on 18/3/8.
 */
public class GiveGreenLightStrategy implements IStrategy {
    @Override
    public void operate() {
        System.out.println("找吴国太开个绿灯，放行");
    }
}
