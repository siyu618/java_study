package com.study.java8.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Handle {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1/0;
            return 100;
        });
        CompletableFuture<String> f = future.handleAsync((v,e)-> v.toString() + ":00");
        f = f.exceptionally((e) ->  "ERROR");
        System.out.println(f.get());
        System.out.println();
    }
}
