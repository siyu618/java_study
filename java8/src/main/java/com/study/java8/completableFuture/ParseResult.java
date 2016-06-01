package com.study.java8.completableFuture;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

/**
 * Created by tianyuzhi on 16/6/1.
 */
public class ParseResult {
    private static Random random = new Random();
    private static long t = System.currentTimeMillis();

    static int getMore() {
        System.out.print("begin to start compute");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End to start compute. passed " + (System.currentTimeMillis() - t)/1000 + " seconds");
        return random.nextInt();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(ParseResult::getMore);
        Future<Integer> f = future.whenComplete((v, e) ->
                {
                    System.out.println(v);
                    System.out.println(e);
                }
        );
        System.out.println(f.get());
        System.in.read();
        System.out.println();

    }
}
