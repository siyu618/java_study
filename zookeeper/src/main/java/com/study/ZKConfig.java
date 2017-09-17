package com.study;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Getter;

/**
 * Created by tianyuzhi on 17/9/14.
 */
@Getter
public class ZKConfig {
    private String hostPort;
    private int sessionTimeout;
    private int connectionTimeout;
    private String namespace;

    public ZKConfig(Config config) {
        this.hostPort = config.getString("zookeeper.zk_host");
        this.sessionTimeout = config.getInt("zookeeper.session_timeout");
        this.connectionTimeout = config.getInt("zookeeper.connection_timeout");
        this.namespace = config.getString("zookeeper.namespace");
    }

    public static ZKConfig defaultConfig() {
        Config config = ConfigFactory.load();
        Config zkConfig = config.getConfig("zookeeper");
        return new ZKConfig(config);
    }


}
