package com.study.design_pattern.factory.product;

/**
 *
 * @author tianyuzhi
 * @date 18/3/6
 */
public abstract class Creator {
    public abstract <T extends Product> T create(Class<T> clazz);
}
