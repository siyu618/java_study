package com.study.easyclient.futureutils;

import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.testng.Assert.*;

/**
 * @author jixu
 */
public class FutureUtilsTest {
    @Test
    public void testCollect() throws Exception {
        List<CompletableFuture<Integer>> futures = Lists.newArrayList();
        for (int i = 0; i < 5; ++i) {
            futures.add(new CompletableFuture<>());
        }
        CompletableFuture<List<Integer>> collectedFuture = FutureUtils.collect(futures);
        futures.get(0).complete(0);
        Thread.sleep(500);
        assertFalse(collectedFuture.isDone());
        for (int i = 1; i < 5; ++i) {
            futures.get(i).complete(i);
        }
        assertTrue(collectedFuture.isDone());
        List<Integer> integers = collectedFuture.join();
        assertEquals(integers, Lists.newArrayList(0, 1, 2, 3, 4));
    }
    @Test
    public void testCollectMap() throws Exception {
        Map<Integer, CompletableFuture<Integer>> futureMap = Maps.newHashMap();
        for (int i = 0; i < 5; ++i) {
            futureMap.put(i, new CompletableFuture<>());
        }
        CompletableFuture<Map<Integer, Integer>> collectedFutureMap = FutureUtils.collect(futureMap);
        futureMap.get(0).complete(0);
        Thread.sleep(500);
        assertFalse(collectedFutureMap.isDone());
        for (int i = 1; i < 5; ++i) {
            futureMap.get(i).complete(i);
        }
        assertTrue(collectedFutureMap.isDone());
        Map<Integer, Integer> integerMap = collectedFutureMap.join();
        assertEquals(integerMap, Maps.newHashMap(0,0, 1,1, 2,2, 3,3, 4,4));
    }
}
