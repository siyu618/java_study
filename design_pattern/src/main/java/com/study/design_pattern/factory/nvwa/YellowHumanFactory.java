package com.study.design_pattern.factory.nvwa;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class YellowHumanFactory extends AbstractHumanFactory {
    @Override
    public Human createHuman() {
        return new YellowHuman();
    }
}
