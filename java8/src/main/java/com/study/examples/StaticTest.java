package com.study.examples;

import java.util.List;
/**
 * Created by tianyuzhi on 17/6/20.
 */
public class StaticTest {
    public static void main(String[] args)
    {
        staticFunction();
    }

    static StaticTest st = new StaticTest();

    static
    {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    StaticTest()
    {
        System.out.println("3");
        System.out.println("a="+a+",b="+b);
    }

    public int maxDistance(List<List<Integer>> arrays) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (List<Integer> arr : arrays) {
            if (arr != null && arr.size() > 0) {
                max = Math.max(max, arr.get(arr.size() - 1));
                min = Math.min(min, arr.get(0));
            }
        }
        return Math.abs(max - min);
    }

    public static void staticFunction(){
        System.out.println("4");

    }

    int a=110;
    static int b =112;
}
