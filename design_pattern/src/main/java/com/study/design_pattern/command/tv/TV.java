package com.study.design_pattern.command.tv;

/**
 * Created by tianyuzhi on 18/3/12.
 */
public class TV {
    private int currentChanel = 0;
    public void turnOn() {
        System.out.println("The television is on");
    }
    public void turnOff() {
        System.out.println("The television is off");
    }
    public void changeChannel(int channel) {
        this.currentChanel = channel;
        System.out.println("Now TV channel is " + channel);
    }
}
