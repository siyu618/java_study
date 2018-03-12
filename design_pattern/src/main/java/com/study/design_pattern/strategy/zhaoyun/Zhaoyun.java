package com.study.design_pattern.strategy.zhaoyun;

/**
 * Created by tianyuzhi on 18/3/8.
 */
public class Zhaoyun {
    public static void main(String... args) {
        Context context = null;
        System.out.println("刚到吴国拆开第一个镜囊");
        context = new Context(new BackDoorStrategy());
        context.operate();

        System.out.println("刘备乐不思蜀，拆开第二个镜囊");
        context = new Context(new GiveGreenLightStrategy());
        context.operate();

        System.out.println("孙权的追兵，拆开第三个镜囊");
        context = new Context(new BlockEnemyStrategy());
        context.operate();
    }
}
