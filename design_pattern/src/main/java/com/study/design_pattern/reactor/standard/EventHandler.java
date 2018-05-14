package com.study.design_pattern.reactor.standard;

/**
 * Created by tianyuzhi on 18/4/20.
 */
public abstract class EventHandler {
    Source source;

    public abstract void handle(Event event);
    public Source getSource(){
        return source;
    }
}
