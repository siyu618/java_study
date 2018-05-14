package com.study.design_pattern.reactor.standard;

/**
 * Created by tianyuzhi on 18/4/20.
 */
public class AcceptEventHandler extends  EventHandler {
    private Demultiplexer selector;

    public AcceptEventHandler(Demultiplexer selector) {
        this.selector = selector;
    }

    @Override
    public void handle(Event event) {
        if (event.eventType == EventType.ACCEPT) {
            Event readEvent = new Event();
            readEvent.source = event.source;
            readEvent.eventType = EventType.READ;
            selector.addEvent(readEvent);
        }
    }
}
