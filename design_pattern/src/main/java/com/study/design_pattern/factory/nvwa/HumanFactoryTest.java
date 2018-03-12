package com.study.design_pattern.factory.nvwa;

import java.util.Arrays;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class HumanFactoryTest {
    public static void main(String[] args) {
        Human black = new BlackHumanFactory().createHuman();
        Human yellow = new YellowHumanFactory().createHuman();
        Human white = new YellowHumanFactory().createHuman();
        for (Human human : Arrays.asList(black, yellow, white)) {
            human.talk();
            human.getColor();
        }
    }
}
