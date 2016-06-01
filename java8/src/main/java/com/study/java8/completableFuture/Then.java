package com.study.java8.completableFuture;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Then {
    public void test() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->
        {
            return 100;
        });
        CompletableFuture<String> f = future.thenApplyAsync(i->i*10).thenApply(i->i.toString());
        System.out.println(f.get());
    }

    public void testAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->
        {
            return 100;
        });
        CompletableFuture<String> f = future.thenApplyAsync(i->i*10).thenApply(i -> i.toString());
        f.thenAccept(System.out::println);
    }
    public void testAcceptBoth() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->
        {
            return 100;
        });
        CompletableFuture<Void> f = future.thenAcceptBoth(CompletableFuture.completedFuture(9), (x, y) -> System.out.println(x * y));
        f.get();
    }

    public void testRun() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->
        {
            return 100;
        });
        CompletableFuture<Void> f = future.thenRun(() -> System.out.println("Finished"));
        f.get();
    }

    public void testCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->
        {
            return 100;
        });
        CompletableFuture<String> f = future.thenCompose(i -> {
            return CompletableFuture.supplyAsync(() -> i * 10 + "");
        });
        System.out.println(f.get());
    }

    public void testCombine() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->
        {
            return 100;
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "abc");
        CompletableFuture<String> f = future.thenCombine(future2, (x, y) -> y + "-" + x);
        System.out.println(f.get());
    }

    public void testEither() throws ExecutionException, InterruptedException {
        Random random = new Random();
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        Thread.sleep(10000 + random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 100;
                }
        );

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        Thread.sleep(10000 + random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 200;
                }
        );
        CompletableFuture<String> f = future.applyToEither(future2, i -> i.toString());
        System.out.println(f.get());
    }

    public void testOf() throws ExecutionException, InterruptedException {
        Random random = new Random();
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        Thread.sleep(10000 + random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 100;
                }
        );

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        Thread.sleep(10000 + random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "abc";
                }
        );
        //CompletableFuture<Object> f = CompletableFuture.anyOf(future, future2);
        CompletableFuture<Void> f = CompletableFuture.allOf(future, future2);
        System.out.println(f.get());
        System.out.println(f);
    }


//    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> features) {
//        CompletableFuture<Void> allDoneFeatures = CompletableFuture.allOf(features.toArray(new CompletableFuture[features.size()]));
//        return allDoneFeatures.thenApply(v->features.stream().map(CompletableFuture::join).collect(Collectors.toList()));
//    }
//
//    public static <T> CompletableFuture<Stream<T>> sequence2(Stream<CompletableFuture<T>> futures) {
//        List<CompletableFuture<T>> futureList = futures.filter(f->f!=null).collect(Collectors.toList());
//        return sequence(futureList);
//    }


    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.<T>toList()));
    }
    public static <T> CompletableFuture<List<T>> sequence(Stream<CompletableFuture<T>> futures) {
        List<CompletableFuture<T>> futureList = futures.filter(f -> f != null).collect(Collectors.toList());
        return sequence(futureList);
    }

    public static <T> CompletableFuture<T> toCompletable(Future<T> future, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }, executor);
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Then then = new Then();
        then.test();
        then.testAccept();
        then.testAcceptBoth();
        then.testRun();
        then.testCompose();
        then.testCombine();
        then.testEither();
        then.testOf();

        System.out.println();
    }
}
