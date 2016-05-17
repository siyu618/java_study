package com.study.test;

/**
 * Created by tianyuzhi on 16/5/17.
 */
public class Drived extends Base{
    public void A() {
        System.out.println("Drived:A()");
        B();
    }
    @Override
    public void B() {
        System.out.println("Drived:B()");

    }

    public static void main(String[] args) {
        Base base = new Drived();
        base.A();;
    }
}
