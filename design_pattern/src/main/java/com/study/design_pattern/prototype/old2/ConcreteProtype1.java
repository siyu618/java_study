package com.study.design_pattern.prototype.old2;


/**
 * Created by tianyuzhi on 18/3/9.
 */
public class ConcreteProtype1 implements Prototype {
    private String name;


    @Override
    public Object clone() {
        ConcreteProtype1 protype = new ConcreteProtype1();
        protype.setName(this.name);
        return protype;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Now in Protoytpe1, name:" + this.name;
    }
}

