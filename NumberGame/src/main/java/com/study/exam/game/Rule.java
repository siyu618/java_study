package com.study.exam.game;

import com.study.exam.game.exception.InvalidNumberEntity;

/**
 * Created by tianyuzhi on 17/7/3.
 */
public interface Rule<V > {
    RuleResult apply(V v, int number) throws InvalidNumberEntity;
}
