package com.study.exam.service;

import java.util.*;

/**
 * Created by tianyuzhi on 17/7/4.
 */

public class FBWGameService {
    private static final int MAX = 100;

    private void printResult(List<String> list) {
        if (null != list) {
            for (String str : list) {
                System.out.println(str);
            }
        }
    }

    public void process(int a, int b, int c) {
        FBWGame fbwGame = new FBWGame(a, b, c);
        if (!fbwGame.isValid()) {
            return;
        }
        List<String> result = new ArrayList<>(MAX);
        for (int i = 1; i <= MAX; i ++) {
            result.add(fbwGame.apply(i));
        }
        printResult(result);
    }

    public static void main(String[] args) {
        FBWGameService fbwGameService = new FBWGameService();
        fbwGameService.process(3,5,7);
    }




    public static class FBWGame {
        private static final String Fizz = "Fizz";
        private static final String Buzz = "Buzz";
        private static final String Whizz = "Whizz";

        private Map<Integer, String> gameItem;
        private List<Integer> gameNumbers;
        private String firstGameNumberStr;

        public FBWGame(int f, int b, int w) {
            gameNumbers = new ArrayList<>(3);
            gameNumbers.add(f);
            gameNumbers.add(b);
            gameNumbers.add(w);
            firstGameNumberStr = String.valueOf(f);

            gameItem = new LinkedHashMap<>();
            gameItem.put(f, Fizz);
            gameItem.put(b, Buzz);
            gameItem.put(w, Whizz);
        }

        public boolean isValid() {
            Set<Integer> set = new HashSet<>();
            for (Integer i : gameNumbers) {
                if (set.contains(i) || i <= 0 || i >= 10) {
                    return false;
                }
                set.add(i);
            }
            return true;
        }

        private boolean containsFirstGameNumber(int number) {
            if (String.valueOf(number).indexOf(firstGameNumberStr) != -1) {
                return true;
            }
            return false;
        }

        private boolean timesOfGameNumbers(int number) {
            for (Integer gameNum : gameNumbers) {
                if (number % gameNum == 0) {
                    return true;
                }
            }
            return false;
        }

        public String apply(int number) {
            if (number <= 0) {
                return null;
            }
            if (containsFirstGameNumber(number)) {
                return Fizz;
            } else if (timesOfGameNumbers(number)) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<Integer, String> entry : gameItem.entrySet()) {
                    if (number % entry.getKey() == 0) {
                        sb.append(entry.getValue());
                    }
                }
                return sb.toString();
            } else {
                return String.valueOf(number);
            }
        }
    }
}

