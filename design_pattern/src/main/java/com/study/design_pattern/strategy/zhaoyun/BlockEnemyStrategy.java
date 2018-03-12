package com.study.design_pattern.strategy.zhaoyun;

/**
 * Created by tianyuzhi on 18/3/8.
 */
public class BlockEnemyStrategy implements IStrategy {
    @Override
    public void operate() {
        System.out.println("孙夫人断后，挡住追兵");
    }
}
