package com.study.design_pattern.command.tv;

/**
 * Created by tianyuzhi on 18/3/12.
 */
public class Control {
    private Command onCommand;
    private Command offCommand;
    private Command changeChannelCommand;

    public Control(Command onCommand, Command offCommand, Command changeChannelCommand) {
        this.onCommand = onCommand;
        this.offCommand = offCommand;
        this.changeChannelCommand = changeChannelCommand;
    }

    public void turnOn() {
        onCommand.execute();
    }
    public void turnOff() {
        offCommand.execute();
    }
    public void change() {
        changeChannelCommand.execute();
    }
}
