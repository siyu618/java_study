package com.study.exam.game.impl;

import org.testng.annotations.Test;

/**
 * Created by tianyuzhi on 17/7/3.
 */
public class ThreeNumberEntityTest {
    @Test
    public void testIsValid() throws Exception {
        ThreeNumberEntity entity = new ThreeNumberEntity(3,5,7);
        ThreeNumberEntity entity1 = new ThreeNumberEntity(1,3,3);
        assert entity.isValid();
        assert !entity1.isValid();
    }

}