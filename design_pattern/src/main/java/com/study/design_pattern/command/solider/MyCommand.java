package com.study.design_pattern.command.solider;


/**
 * Created by tianyuzhi on 18/3/12.
 */
public class MyCommand implements Command {
    private Receiver receiver;
    public MyCommand(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public void execution() {
        receiver.action();
    }
}
