package com.study.guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Created by tianyuzhi on 16/5/16.
 */
public class BiMapTest {
    public static void main(String[] args) {
        BiMap<Integer, String> biMap = HashBiMap.create();
        biMap.put(101, "Mahesh");
        biMap.put(102, "Sohan");
        biMap.put(103, "Ramesh");

        System.out.println(biMap.inverse().get("Mahesh"));
    }
}
