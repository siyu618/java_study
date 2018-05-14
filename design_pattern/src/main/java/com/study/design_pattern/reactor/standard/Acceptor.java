package com.study.design_pattern.reactor.standard;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by tianyuzhi on 18/4/20.
 */
public class Acceptor implements Runnable {
    private int port;
    private Demultiplexer selector;

    private BlockingQueue<Source> sourceBlockingQueue = new LinkedBlockingDeque<>();

    public Acceptor(Demultiplexer selector, int port ) {
        this.selector = selector;
        this.port = port;
    }

    public void addNewConnection(Source source) {
        sourceBlockingQueue.add(source);
    }

    public int getPort() {
        return this.port;
    }

    @Override
    public void run() {
        while (true) {

            Source source = null;

            try {
                source = sourceBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (null != source) {
                Event acceptEvent = new Event();
                acceptEvent.eventType = EventType.ACCEPT;
                acceptEvent.source = source;
                selector.addEvent(acceptEvent);
            }
        }
    }
}
