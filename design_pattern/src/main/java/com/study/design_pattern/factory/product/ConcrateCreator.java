package com.study.design_pattern.factory.product;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class ConcrateCreator extends Creator {
    @Override
    public <T extends Product> T create(Class<T> clazz) {
        Product product = null;
        try {
            product = (Product)Class.forName(clazz.getName()).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T)product;
    }


    public static void main(String[] args) {
        Creator factory = new ConcrateCreator();
        Product product1 = factory.create(ConcreateProduct1.class);
        Product product2 = factory.create(ConcreateProduct2.class);

        product1.method();
        product2.method();
    }
}
