package com.study.justtest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianyuzhi on 17/4/26.
 */
public class IntegerTest {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("rank", "abc");
        map.put("count", Integer.valueOf(50));
        map.put("count2", null);

        int count = 5;
        Integer attributeCount = (Integer)map.get("count");
        if (null != attributeCount) {
            count = attributeCount.intValue();
        }

        int count2 = 5;
        Integer  attributeCount2 = (Integer)map.get("count2");
        if (null != attributeCount2) {
            count2 = attributeCount2.intValue();
        }

        System.out.println("count=" + count);
        System.out.println("count2=" + count2);
    }

}
