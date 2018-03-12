package com.study.design_pattern.proxy.person;

/**
 * Created by tianyuzhi on 18/2/28.
 */
public class LiuDeHua implements IPerson {
    @Override
    public String sing(String name) {
        System.out.println("liudehua sing " + name + "!");
        return "Sing. Thank everyone";
    }

    @Override
    public String dance(String name) {
        System.out.println("liudehua dance " + name + "!");
        return "Dance. Thank everyone";
    }
}
