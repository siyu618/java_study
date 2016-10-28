package com.study.easyclient.futureutils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * reference: http://www.nurkiewicz.com/2014/12/asynchronous-timeouts-with.html
 *            http://www.nurkiewicz.com/2013/05/java-8-completablefuture-in-action.html
 * @author jixu
 */
public class FutureUtils {
    private static final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(24,
                    new ThreadFactoryBuilder()
                            .setDaemon(true)
                            .setNameFormat("failAfter-%d")
                            .build());

    public static <T> CompletableFuture<T> failAfter(long timeout, TimeUnit timeUnit) {
        final CompletableFuture<T> promise = new CompletableFuture<>();
        scheduler.schedule(() -> {
            final TimeoutException ex = new TimeoutException("Timeout after " + timeout + timeUnit);
            return promise.completeExceptionally(ex);
        }, timeout, timeUnit);
        return promise;
    }

    public static <T> CompletableFuture<T> within(CompletableFuture<T> future, long timeout, TimeUnit timeUnit) {
        final CompletableFuture<T> timeoutFuture = failAfter(timeout, timeUnit);
        return future.applyToEither(timeoutFuture, Function.identity());
    }

    public static <T> CompletableFuture<List<T>> collect(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.<T>toList()));
    }

    public static <K,V> CompletableFuture<Map<K,V>> collect(Map<K,CompletableFuture<V>> futureMap) {
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futureMap.values().toArray(new CompletableFuture[futureMap.size()]));
        return allDoneFuture.thenApply(v -> futureMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().join())));
    }
}
