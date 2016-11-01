package com.study.metrics.reporter.opentsdb;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by xuqingxing on 16/7/21.
 */
public class MetricNameFilter {

    private static Pattern filterPattern = Pattern.compile("[^A-Za-z0-9_\\-\\.]");

    public static List<OpenTsdbMetric> filter(List<OpenTsdbMetric> originMetrics){
        return originMetrics.stream()
                .filter(openTsdbMetric -> filter(openTsdbMetric.getMetric()))
                .collect(Collectors.toList());
    }

    public static boolean filter(String originMetricName){
        if(originMetricName == null){
            return false;
        }
        return !filterPattern.matcher(originMetricName).find();
    }


    public static String filterStr(String originMetricName){
        if(originMetricName == null){
            return "";
        }
        Matcher filterMatcher = filterPattern.matcher(originMetricName);
        String result = null;
        if (filterMatcher.find()){
            result = filterMatcher.replaceAll("");
        }
        return result;
    }

    public static void main(String[] args) {

        System.out.println(filter("slave-class redis.clients.jedis.exceptions.JedisConnectionException.m1"));

        OpenTsdbMetric openTsdbMetric1 = new OpenTsdbMetric("slave-class redis.clients.jedis.exceptions.JedisConnectionException.m1", 0L, null, null);
        OpenTsdbMetric openTsdbMetric2 = new OpenTsdbMetric("slave-class_redis.clients.jedis.exceptions.JedisConnectionException.m1={}", 0L, null, null);
        OpenTsdbMetric openTsdbMetric3 = new OpenTsdbMetric("slave-class_redis.clients.jedis.exceptions.JedisConnectionException.m1", 0L, null, null);

        List<OpenTsdbMetric> openTsdbMetrics = Lists.newArrayList();
        openTsdbMetrics.add(openTsdbMetric1);
        openTsdbMetrics.add(openTsdbMetric2);
        openTsdbMetrics.add(openTsdbMetric3);

        System.out.println(filter(openTsdbMetrics).size());

    }

}
