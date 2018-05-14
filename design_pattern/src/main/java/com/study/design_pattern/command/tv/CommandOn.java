package com.study.design_pattern.command.tv;

/**
 * Created by tianyuzhi on 18/3/12.
 */
public class CommandOn implements Command {
    private TV tv;
    public CommandOn(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.turnOn();
    }
}
