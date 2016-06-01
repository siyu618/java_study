package com.study.java8.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by tianyuzhi on 16/6/1.
 */
public class CompletableFutureTest {

    public void testException() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->{
            int i = 1/0;
            return 100;
        });
        System.out.println(future);
        future = future.exceptionally((e) -> {
            System.out.println(e);
            return -1;
        });
        //future.join();
        System.out.println(future);
        System.out.println(future.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFutureTest test = new CompletableFutureTest();
        test.testException();

    }
}
