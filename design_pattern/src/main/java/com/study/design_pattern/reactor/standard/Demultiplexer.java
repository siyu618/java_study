package com.study.design_pattern.reactor.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by tianyuzhi on 18/4/20.
 */
public class Demultiplexer {
    private BlockingQueue<Event> eventQueue = new LinkedBlockingDeque<>();
    private Object lock = new Object();

    public List<Event> select() {
        return select(0);
    }

    private List<Event> select(long timeout) {
        if (timeout > 0) {
            if (eventQueue.isEmpty()) {
                synchronized (lock) {
                    if (eventQueue.isEmpty()) {
                        try {
                            lock.wait(timeout);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        List<Event> events = new ArrayList<>();
        eventQueue.drainTo(events);
        return events;
    }

    public void addEvent(Event e) {
        boolean success = eventQueue.offer(e);
        if (success) {
            synchronized (lock) {
                lock.notifyAll();
            }
        }

    }
}
