package com.study.directmemory;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/1/23.
 */
public class DirectByteBufferTest2 {

    public static void main(String[] args) throws InterruptedException {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 128);
        TimeUnit.SECONDS.sleep(60);
        System.out.println("ok");
        System.out.println(byteBuffer.position());
        ((DirectBuffer)byteBuffer).cleaner().clean();
        TimeUnit.SECONDS.sleep(600);
        System.out.println("done");
    }
    /**
     */
}
