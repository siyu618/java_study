package com.study.reactor.config;

import lombok.Getter;

/**
 * @author didi
 */

@Getter
public class MultiReactorServerConfig {
    private int serverPort;
    private int inputBufferSize;
    private int outputBufferSize;
    private int multiThreadThreadPoolSize = 10;
    private int selectorNum = 2;

    public MultiReactorServerConfig(int serverPort,
                                    int inputBufferSize,
                                    int outputBufferSize,
                                    int multiThreadThreadPoolSize,
                                    int selectorNum) {
        this.inputBufferSize = inputBufferSize;
        this.serverPort = serverPort;
        this.outputBufferSize = outputBufferSize;
        this.multiThreadThreadPoolSize = multiThreadThreadPoolSize;
        this.selectorNum = selectorNum;
    }

}
