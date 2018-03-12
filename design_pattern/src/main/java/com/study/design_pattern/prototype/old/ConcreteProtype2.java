package com.study.design_pattern.prototype.old;

/**
 * Created by tianyuzhi on 18/3/9.
 */
public class ConcreteProtype2 implements Prototype {
    @Override
    public Object clone() {
        return new ConcreteProtype2();
    }
}
