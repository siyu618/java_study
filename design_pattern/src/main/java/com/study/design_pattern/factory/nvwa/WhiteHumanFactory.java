package com.study.design_pattern.factory.nvwa;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class WhiteHumanFactory extends AbstractHumanFactory {
    @Override
    public Human createHuman() {
        return new WhiteHuman();
    }
}
