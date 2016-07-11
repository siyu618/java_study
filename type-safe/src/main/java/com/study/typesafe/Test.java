package com.study.typesafe;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigValue;

import java.util.Map;
import java.util.Properties;

/**
 * Created by tianyuzhi on 16/5/19.
 */
public class Test {

    public static void main(String[] args) {
        Config config = ConfigFactory.load();

        System.out.println(System.getProperty("user.dir"));
        System.out.println(config);
        System.out.println(GsonFactory.getPrettyGson().toJson(config));
        Config rootConfig = ConfigFactory.load();
        Properties properties = new Properties();
        if (rootConfig.hasPath("interests-engine")
                && rootConfig.getConfig("interests-engine").hasPath("kafka-log-sender")) {
            Config kafkaConf = rootConfig.getConfig("interests-engine").getConfig("kafka-log-sender");
            for (Map.Entry<String, ConfigValue> entry : kafkaConf.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue().toString());
                System.out.println(entry.getKey() + " " + entry.getValue().render());
                System.out.println(entry.getKey() + " " + entry.getValue().render(ConfigRenderOptions.defaults()));
                System.out.println(entry.getKey() + " " + entry.getValue().unwrapped());
                System.out.println(entry.getKey() + " " + entry.getValue().toString());
                properties.put(entry.getKey(), kafkaConf.getString(entry.getKey()));
            }
            System.out.println(properties);
        }
    }
}
