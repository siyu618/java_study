package com.study.design_pattern.command.tv;

/**
 * Created by tianyuzhi on 18/3/12.
 */
public class Client {
    public static void main(String... args) {
        TV tv = new TV();
        Command onCommand = new CommandOn(tv);
        Command offCommand = new CommandOff(tv);
        Command changeCommand = new CommandChangeChannel(tv, 2);

        Control control = new Control(onCommand, offCommand, changeCommand);
        control.turnOn();
        control.change();
        control.turnOff();
    }

}
