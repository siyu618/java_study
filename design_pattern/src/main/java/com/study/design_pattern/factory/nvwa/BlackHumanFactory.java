package com.study.design_pattern.factory.nvwa;

/**
 *
 * @author tianyuzhi
 * @date 18/3/6
 */
public class BlackHumanFactory extends AbstractHumanFactory {
    @Override
    public Human createHuman() {
        return new BlackHuman();
    }
}
