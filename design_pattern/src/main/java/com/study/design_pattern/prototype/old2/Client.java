package com.study.design_pattern.prototype.old2;

/**
 * Created by tianyuzhi on 18/3/9.
 */
public class Client {
    public static void main(String[] args) {
        Prototype p1 = new ConcreteProtype1();
        PrototypeManager.setPrototype("p1", p1);

        Prototype p3 = (Prototype) PrototypeManager.getPrototype("p1").clone();
        p3.setName("zhangshan");
        System.out.println("first:" + p3);

        Prototype p2 = new ConcreteProtype2();
        PrototypeManager.setPrototype("p1", p2);

        Prototype p4 = (Prototype)PrototypeManager.getPrototype("p1").clone();
        p4.setName("lisi");
        System.out.println("second:" + p4);

        PrototypeManager.removePrototype("p1");

        Prototype p5 = (Prototype)PrototypeManager.getPrototype("p1").clone();
        p5.setName("wangwu");
        System.out.println("3rd:" + p5);


    }
}
