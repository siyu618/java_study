package com.study.design_pattern.command.tv;

/**
 * Created by tianyuzhi on 18/3/12.
 */
public class CommandChangeChannel implements Command {
    private TV tv;
    private int channel;
    public CommandChangeChannel(TV tv, int channel) {
        this.tv = tv;
        this.channel = channel;
    }
    @Override
    public void execute() {
        tv.changeChannel(channel);
    }
}
