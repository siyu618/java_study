package com.study.design_pattern.reactor.standard;

import java.util.Date;

/**
 * Created by tianyuzhi on 18/4/20.
 */
public class Source {

    private Date date = new Date();
    private String id = date.toString() + "_" + System.identityHashCode(date);

    @Override
    public String toString() {
        return id;
    }
}
