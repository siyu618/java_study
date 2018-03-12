package com.study.design_pattern.factory.nvwa;

import java.util.Arrays;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class HumanFactory extends AbstractFactory {
    @Override
    public <T extends Human> T createHuman(Class<T> clazz) {
        Human human = null;
        try {
            human = (Human) Class.forName(clazz.getName()).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T)human;
    }

    public static void main(String[] args) {
        AbstractFactory factory = new HumanFactory();
        Human black = factory.createHuman(BlackHuman.class);
        Human yellow = factory.createHuman(YellowHuman.class);
        Human white = factory.createHuman(WhiteHuman.class);

        for (Human human : Arrays.asList(black, yellow, white)) {
            human.talk();
            human.getColor();
        }
    }
}
