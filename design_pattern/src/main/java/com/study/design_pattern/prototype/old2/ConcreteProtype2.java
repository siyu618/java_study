package com.study.design_pattern.prototype.old2;


/**
 * Created by tianyuzhi on 18/3/9.
 */
public class ConcreteProtype2 implements Prototype {
    private String name;


    @Override
    public Object clone() {
        ConcreteProtype2 protype = new ConcreteProtype2();
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
        return "Now in Protoytpe2, name:" + this.name;
    }
}
