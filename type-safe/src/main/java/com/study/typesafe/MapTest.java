package com.study.typesafe;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tianyuzhi on 16/5/20.
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String, String > map = new LinkedHashMap<>(3,0.75f,true);
        map.put("1", "2");
        map.put("2", "2");
        map.put("3", "2");
        System.out.println(map);
        map.put("1", "2");
        map.put("2", "2");
        map.put("4", "5");
        map.put("6", "5");
        System.out.println(map);

    }
}
