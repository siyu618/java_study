package com.study.examples;


import java.util.Arrays;
import java.util.List;

public class Judge {
    // O(n*n*n)  容易想到
    // 寻找单调递增子数组，长度>=3 即可， dp O(n*n)
    // 貌似有nlogn 的算法：（值，index） ？
    boolean canFindIncThree(int[] nums) {
        if (null == nums || nums.length < 3) {
            return false;
        }
        int len = nums.length;
        int[] dp = new int[len];
        for (int i = 0; i < len; i++) {
            dp[i] = 1;
        }
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    if (dp[i] > 3) {
                        return true;
                    }
                }
            }
        }
        int max = 1;
        for (int i = 0; i < len; i++) {
            max = Math.max(max, dp[i]);
        }
        return max >= 3;
    }

    public static void main(String[] args) {
        List<int[]> lists = Arrays.asList(
                new int[]{1, 2, 4, 6},
                new int[]{4, 5, 0, 1},
                new int[]{1, 4, 6},
                new int[]{1},
                new int[]{1, 2},
                new int[]{1},
                null
        );

        Judge judge = new Judge();
        for (int[] nums : lists) {
            System.out.println(judge.canFindIncThree(nums));
        }
    }
}
