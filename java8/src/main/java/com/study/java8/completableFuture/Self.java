package com.study.java8.completableFuture;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by tianyuzhi on 16/6/1.
 */
public class Self {
    public static CompletableFuture<Integer> compute() {
        final CompletableFuture<Integer> f = new CompletableFuture<>();
        return f;
    }

    public static void main(String[] args) throws IOException {
        final CompletableFuture<Integer> f = compute();
        class Client extends Thread {
            CompletableFuture<Integer> f;
            Client(String name, CompletableFuture<Integer> f) {
                super(name);
                this.f = f;
            }

            @Override
            public void run() {
                try {
                    System.out.println(this.getName() + ": " + f.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        new Client("client1", f).start();
        new Client("client2", f).start();
        f.complete(100);
        System.in.read();
    }
}
