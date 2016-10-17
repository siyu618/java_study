package com.study.concurrenty.thread_safty;

/**
 * Created by tianyuzhi on 16/10/13.
 */
public class NotThreadSafeCal {
    // currentValue is not thread safe


    private ImmutableValue currentVal = null;

    public ImmutableValue getValue() {
        return currentVal;
    }

    public void setValue(ImmutableValue newVale) {
        this.currentVal = newVale;
    }

    public void add(int newValue) {
        this.currentVal = this.currentVal.add(newValue);
    }
}
