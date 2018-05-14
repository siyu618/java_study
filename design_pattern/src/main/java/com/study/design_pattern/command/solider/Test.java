package com.study.design_pattern.command.solider;

/**
 * Created by tianyuzhi on 18/3/12.
 */
public class Test {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command = new MyCommand(receiver);
        Invoker invoker = new Invoker(command);
        invoker.action();
    }
}
