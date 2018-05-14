package com.study.design_pattern.command.solider;

/**
 * Created by tianyuzhi on 18/3/12.
 */
public class Invoker {
    private Command command;
    public Invoker(Command command) {
        this.command = command;
    }

    public void action() {
        command.execution();
    }
}
