package com.study.exam.game;

import java.util.Map;

/**
 * Created by tianyuzhi on 17/7/4.
 */
public interface NumberEntity {
    boolean isValid();
    Map<Integer, String> getNumber2StringMap();
}
