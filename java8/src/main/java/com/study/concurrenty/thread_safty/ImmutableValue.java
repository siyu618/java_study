package com.study.concurrenty.thread_safty;

/**
 * Created by tianyuzhi on 16/10/13.
 */
public class ImmutableValue {
    private int value = 0;
    public ImmutableValue(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public ImmutableValue add(int val) {
        return new ImmutableValue(this.value + val);
    }
}

