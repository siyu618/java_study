package com.study.design_pattern.strategy.zhaoyun;

/**
 * Created by tianyuzhi on 18/3/8.
 */
public class BackDoorStrategy implements IStrategy {
    @Override
    public void operate() {
        System.out.println("找乔国老帮忙，让吴国太给孙权施加压力");
    }
}
