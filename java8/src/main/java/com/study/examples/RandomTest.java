package com.study.examples;

import java.util.Random;

/**
 * Created by tianyuzhi on 16/12/14.
 */
public class RandomTest {
    public static void main(String[] args) {
        Random random = new Random();
        int[] nums = {0,1,-1,2};
        for (int num : nums) {
            System.out.println(num + " : " + random.nextInt(num));

        }
    }
}
