package com.study.design_pattern.strategy.car;

/**
 * Created by tianyuzhi on 18/3/8.
 */
public class Bicycle extends Car {
    public Bicycle() {
        driveBehavior = new RidingDriveBehavior();
        loadBehavior = new LessLoadBehavior();
    }
    @Override
    public void play() {
        System.out.println("我是自行车");
    }

    public static void main(String... args) {
        Car bicycle = new Bicycle();
        bicycle.play();
        bicycle.drive();
        bicycle.load();
    }
}
