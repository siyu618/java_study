package com.study.codis;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Data;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

/**
 * Created by tianyuzhi on 17/6/20.
 */
@Data
public class MemUserHistoryCodisConfig {
    private volatile static MemUserHistoryCodisConfig defaultConfig = null;
    private boolean enableCodis = false;
    private int expireTimeInSeconds = 180;
    private String zkHostAndPort = "";
    private String zkPath = "";
    private int zkSessionTimeoutMs = 30000;
    private boolean enableGetFromThriftServer = true;
    private JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();


    public static MemUserHistoryCodisConfig defaultConfig() {
        if (defaultConfig == null) {
            synchronized (MemUserHistoryCodisConfig.class) {
                if (defaultConfig == null) {
                    defaultConfig = new MemUserHistoryCodisConfig();
                }
            }
        }
        return defaultConfig;
    }

    private MemUserHistoryCodisConfig() {
        Config config = ConfigFactory.load();
        enableCodis = config.getBoolean("userhistory.codis.enable");
        expireTimeInSeconds = config.getInt("userhistory.codis.key.expire.time.in.seconds");
        zkHostAndPort = config.getString("userhistory.codis.zk.address");
        zkPath = config.getString("userhistory.codis.zk.path");
        zkSessionTimeoutMs = config.getInt("userhistory.codis.zk.zkSessionTimeoutMs");
        enableGetFromThriftServer = config.getBoolean("userhistory.codis.get_from_thriftserver.enable");
        String jedisConfigString = config.getString("userhistory.codis.pool.config");
        if (enableCodis && !Strings.isNullOrEmpty(jedisConfigString)) {
            Map<String, String> map = JSON.parseObject(jedisConfigString, Map.class);
            if (null != map) {
                if (map.containsKey("maxIdle")) {
                    jedisPoolConfig.setMaxIdle(Integer.parseInt(map.get("maxIdle")));
                }
                if (map.containsKey("maxTotal")) {
                    jedisPoolConfig.setMaxTotal(Integer.parseInt(map.get("maxTotal")));
                }
                if (map.containsKey("maxWaitMillis")) {
                    jedisPoolConfig.setMaxWaitMillis(Long.parseLong(map.get("maxWaitMillis")));
                }
                if (map.containsKey("testOnBorrow")) {
                    jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(map.get("testOnBorrow")));
                }
            }
        }

    }
}
