package com.study.exam.service;

import com.study.exam.game.NumberGame;
import com.study.exam.game.impl.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by tianyuzhi on 17/7/3.
 */
public class Service {
    private static final int MAX = 100;
    private static final int INPUT_NUMBERS = 3;

    @Data
    @Builder
    public static class Input {
        private int a;
        private int b;
        private int c;
    }

    private List<String> playThreeNumberGame(int a, int b, int c) {
        ThreeNumberEntity threeNumberEntity = new ThreeNumberEntity(a, b, c);
        if (!threeNumberEntity.isValid()) {
            return new ArrayList<>(0);
        }
        NumberGame numberGame = NumberGameImpl.builder()
                .entity(threeNumberEntity)
                .orderedRuleList(Arrays.asList(new ContainsFirstRule(), new TimesRule(), new DefaultRule()))
                .build();
        List<String> resultList = new ArrayList<>(MAX);
        for (int i = 1; i <= MAX; i++) {
            String res = numberGame.apply(i);
            resultList.add(res);
        }
        return resultList;
    }

    private void output(List<String> list) {
        if (null != list) {
            for (String str : list) {
                System.out.println(str);
            }
        }
    }

    public void process(Input input) {
        List<String> resList = playThreeNumberGame(input.a, input.b, input.c);
        output(resList);
    }

    private Input parseInput(String inputLine) {
        if (null == inputLine || inputLine.length() == 0) {
            return null;
        }
        Input input = null;
        String[] arr = inputLine.split(",");
        if (arr.length == INPUT_NUMBERS) {
            int a = Integer.parseInt(arr[0]);
            int b = Integer.parseInt(arr[1]);
            int c = Integer.parseInt(arr[2]);
            input = Input.builder().a(a).b(b).c(c).build();
        }
        return input;
    }


    public static void main(String[] args) {
        Service service = new Service();
//        //example:
//        service.process(Input.builder().a(3).b(5).c(7).build());
//        service.process(Input.builder().a(7).b(8).c(9).build());

        Scanner sc = new Scanner(System.in);
        //while (sc.hasNextLine()) {
        String inputLine = sc.nextLine();
        Input input = service.parseInput(inputLine);
        if (null != input) {
            service.process(input);
        }
        //}

    }
}
