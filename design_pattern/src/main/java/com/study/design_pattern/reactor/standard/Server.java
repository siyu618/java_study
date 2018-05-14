package com.study.design_pattern.reactor.standard;

/**
 * Created by tianyuzhi on 18/4/20.
 */
public class Server {

    Demultiplexer selector = new Demultiplexer();
    EventDispatch eventLooper = new EventDispatch(selector);
    Acceptor acceptor;
    int port;

    public Server(int port ) {
        acceptor = new Acceptor(selector, port);
        this.port = port;
    }

    public void start() {
        eventLooper.registerEventHandler(EventType.ACCEPT, new AcceptEventHandler(selector));
        new Thread(acceptor, "Acceptor-" + acceptor.getPort()).start();
        eventLooper.handleEvents();
    }
}

