package com.study.artofjcp.ch7;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by tianyuzhi on 17/7/10.
 */
public class AtomicReferenceTest {
    public static AtomicReference<User> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        User user = User.builder().name("conan").old(15).build();
        User updateUser = User.builder().name("Shinichi").old(17).build();

        atomicReference.set(user);
        atomicReference.compareAndSet(user, updateUser);
        System.out.println(atomicReference.get().getName());
        System.out.println(atomicReference.get().getOld());
    }


    @Data
    @Builder
    public static class User {
        private String name;
        private int old;
    }

}
