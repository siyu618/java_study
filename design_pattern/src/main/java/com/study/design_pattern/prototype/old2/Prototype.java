package com.study.design_pattern.prototype.old2;

/**
 * Created by tianyuzhi on 18/3/9.
 */
public interface Prototype {
    Object clone();
    String getName();
    void setName(String name);
}
