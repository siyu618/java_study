package com.study.justtest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianyuzhi on 17/4/26.
 */
public class IntegerTest {
    public void test2() {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a+b));
        System.out.println(c.equals(a+b));
        System.out.println(g == (a+b));
        System.out.println(g.equals(a+b));
    }
    public static void main(String[] args) {
        new IntegerTest().test2();
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
