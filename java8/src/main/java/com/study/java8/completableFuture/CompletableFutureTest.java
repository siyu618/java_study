package com.study.java8.completableFuture;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by tianyuzhi on 16/6/1.
 */
public class CompletableFutureTest {

    public void testException1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->{
            int i = 1/0;
            return null;
        });
        System.out.println(future);
        CompletableFuture<Integer>  newFuture = future.thenApplyAsync(x->x/10).exceptionally((e) -> {
            System.out.println(e);
            //return -1;
            return null;
        });
        //future.join();
        System.out.println(newFuture);
        System.out.println(newFuture.get());
    }

    public void testException() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->{
         //   int i = 1/0;
            return null;
        });
        System.out.println(future);
        CompletableFuture<Integer>  newFuture;
//        newFuture = future.thenCompose(x-> null) // will NPE
          newFuture = future.thenCompose(x-> CompletableFuture.completedFuture(null)) // will not NPE

                .thenApply(x->10)
                .exceptionally((e) -> {
                    System.out.println("testException:" + StringUtils.join(ExceptionUtils.getRootCauseStackTrace(e), "\n"));

                    //return -1;
                    return null;
                });
        //future.join();
        System.out.println(newFuture);
        System.out.println(newFuture.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFutureTest test = new CompletableFutureTest();
        test.testException();
        test.testException1();

        CompletableFuture<Integer> future;
        future = CompletableFuture.supplyAsync(() -> null);
        future.thenApply(r -> {System.out.println("xxx:" + r); return 5;});
        System.out.println(future.get());


    }
}
