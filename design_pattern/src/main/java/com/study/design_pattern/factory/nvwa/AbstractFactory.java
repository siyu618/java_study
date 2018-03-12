package com.study.design_pattern.factory.nvwa;

/**
 *
 * @author tianyuzhi
 * @date 18/3/6
 */
public abstract class AbstractFactory {
    public abstract <T extends Human> T createHuman(Class<T> clazz);
}
