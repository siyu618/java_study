package com.study.artofjcp.ch7;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by tianyuzhi on 17/7/10.
 */
public class AtomicIntegerFieldUpdaterTest {
    private static AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.newUpdater(User.class, "old");

    public static void main(String[] args) {
        User conan = User.builder().name("conan").old(10).build();
        System.out.println(a.getAndIncrement(conan));
        System.out.println(a.get(conan));
    }

    @Data
    @Builder
    public static class User {
        private String name;
        public volatile int old;
    }
}
