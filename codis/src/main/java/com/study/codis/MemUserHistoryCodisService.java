package com.study.codis;


import com.study.codis.util.GsonFactory;
import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by tianyuzhi on 17/6/20.
 */
public class MemUserHistoryCodisService {
    private static final Logger logger = LoggerFactory.getLogger(MemUserHistoryCodisService.class);

    private volatile static MemUserHistoryCodisService defaultInstance = null;
    @Getter
    private MemUserHistoryCodisConfig userHistoryCodisConfig;
    private JedisResourcePool jedisResourcePool = null;

    public static MemUserHistoryCodisService getDefaultInstance() {
        if (defaultInstance == null) {
            synchronized (MemUserHistoryCodisService.class) {
                if (defaultInstance == null) {
                    defaultInstance = new MemUserHistoryCodisService(MemUserHistoryCodisConfig.defaultConfig());
                }
            }
        }
        return defaultInstance;
    }

    public MemUserHistoryCodisService(MemUserHistoryCodisConfig memUserHistoryCodisConfig) {
        this.userHistoryCodisConfig = memUserHistoryCodisConfig;
        if (userHistoryCodisConfig.isEnableCodis()) {
            jedisResourcePool = RoundRobinJedisPool.create()
                    .poolConfig(userHistoryCodisConfig.getJedisPoolConfig())
                    .curatorClient(userHistoryCodisConfig.getZkHostAndPort(), userHistoryCodisConfig.getZkSessionTimeoutMs())
                    .zkProxyDir(userHistoryCodisConfig.getZkPath()).build();
        }
    }



    public boolean set(String key, MemUserHistory memUserHistory) {
        if (!userHistoryCodisConfig.isEnableCodis() || null == memUserHistory) {
            return false;
        }
        String ret = null;
        long tsBegin = System.currentTimeMillis();
        try (Jedis jedis = jedisResourcePool.getResource()) {
            ret = jedis.setex(key, userHistoryCodisConfig.getExpireTimeInSeconds(),
                    GsonFactory.getDefaultGson().toJson(memUserHistory));
           // MetricsFactoryUtil.getRegisteredFactory().getHistogram("UserHistoryCodis.set.error").update( 0);
            logger.info("set to codis with id = {} and got result{}", key, ret);
        } catch (Exception e) {
            e.printStackTrace();
//            MetricsFactoryUtil.getRegisteredFactory().getHistogram("UserHistoryCodis.set.error").update( 100);
        }
//        MetricsFactoryUtil.getRegisteredFactory().getMeter("UserHistoryCodis.set.qps").mark();
//        MetricsFactoryUtil.getRegisteredFactory().getHistogram("UserHistoryCodis.set.latency").update(System.currentTimeMillis() - tsBegin);
        return true;
    }

    public MemUserHistory get(String key) {
        if (!userHistoryCodisConfig.isEnableCodis()) {
            return null;
        }
        MemUserHistory res = null;
        long tsBegin = System.currentTimeMillis();
        try (Jedis jedis = jedisResourcePool.getResource()) {
            res = GsonFactory.getDefaultGson().fromJson(jedis.get(key),
                    MemUserHistory.class);
           // MetricsFactoryUtil.getRegisteredFactory().getHistogram("UserHistoryCodis.get.error").update( 0);
        } catch (Exception e) {
         //  MetricsFactoryUtil.getRegisteredFactory().getHistogram("UserHistoryCodis.get.error").update( 100);
        }
        //MetricsFactoryUtil.getRegisteredFactory().getHistogram("UserHistoryCodis.get.latency").update(System.currentTimeMillis() - tsBegin);
        //MetricsFactoryUtil.getRegisteredFactory().getMeter("UserHistoryCodis.get.qps").mark();
        if (null != res) {
           // MetricsFactoryUtil.getRegisteredFactory().getHistogram("UserHistoryCodis.cache.miss").update( 0);
        }
        else {
            //MetricsFactoryUtil.getRegisteredFactory().getHistogram("UserHistoryCodis.cache.miss").update( 100);
        }
        return res;
    }
}
