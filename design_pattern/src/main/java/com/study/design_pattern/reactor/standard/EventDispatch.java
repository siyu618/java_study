package com.study.design_pattern.reactor.standard;

import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author tianyuzhi
 * @date 18/4/20
 */
public class EventDispatch {
    Map<EventType, EventHandler> eventTypeEventHandlerMap = new ConcurrentHashMap<>();

    Demultiplexer selector;

    public EventDispatch(Demultiplexer selector) {
        this.selector = selector;
    }

    public void registerEventHandler(EventType eventType, EventHandler eventHandler) {
        eventTypeEventHandlerMap.put(eventType, eventHandler);
    }

    public void removeEventHandler(EventType eventType) {
        eventTypeEventHandlerMap.remove(eventType);
    }

    public void handleEvents() {
        dispatch();
    }

    private void dispatch() {
        while (true) {
            List<Event> eventList = selector.select();

            for (Event event : eventList) {
                EventHandler eventHandler = eventTypeEventHandlerMap.get(event.eventType);
                eventHandler.handle(event);
            }
        }
    }
}
