package com.study.reactor.config;

import lombok.Getter;

/**
 * @author didi
 */

@Getter
public class ServerConfig {
    private int serverPort;
    private int inputBufferSize;
    private int outputBufferSize;
    private int multiThreadThreadPoolSize = 10;

    public ServerConfig(int serverPort, int inputBufferSize, int outputBufferSize, int multiThreadThreadPoolSize) {
        this.inputBufferSize = inputBufferSize;
        this.serverPort = serverPort;
        this.outputBufferSize = outputBufferSize;
        this.multiThreadThreadPoolSize = multiThreadThreadPoolSize;
    }

}
