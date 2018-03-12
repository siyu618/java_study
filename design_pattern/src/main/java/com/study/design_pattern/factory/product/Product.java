package com.study.design_pattern.factory.product;

/**
 *
 * @author tianyuzhi
 * @date 18/3/6
 */
public abstract class Product {
    public void method() {
        System.out.println("product method");
        method2();
    }

    public abstract void method2();
}
