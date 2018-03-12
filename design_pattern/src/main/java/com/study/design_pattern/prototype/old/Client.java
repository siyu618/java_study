package com.study.design_pattern.prototype.old;

/**
 * Created by tianyuzhi on 18/3/9.
 */
public class Client {
    private Prototype prototype;

    public Client(Prototype prototype) {
        this.prototype = prototype;
    }

    public void operation(Prototype prototype){}
}
