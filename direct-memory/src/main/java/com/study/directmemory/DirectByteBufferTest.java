package com.study.directmemory;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/1/23.
 */
public class DirectByteBufferTest {

    public static void main(String[] args) throws InterruptedException {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 128);
        TimeUnit.SECONDS.sleep(10);
        System.out.println("ok");
        System.out.println(byteBuffer.position());

    }
    /**
     * -Xmx100m
     * Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
     at java.nio.Bits.reserveMemory(Bits.java:658)
     at java.nio.DirectByteBuffer.<init>(DirectByteBuffer.java:123)
     at java.nio.ByteBuffer.allocateDirect(ByteBuffer.java:311)
     at com.study.directmemory.DirectByteBufferTest.main(DirectByteBufferTest.java:12)
     at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     at java.lang.reflect.Method.invoke(Method.java:497)
     at com.intellij.rt.execution.application.AppMain.main(AppMain.java:140)


     **
     * -Xmx256m
     * OK
     *
     *
     **
     *
     * -Xmx256m -XX:MaxDirectMemorySize=100M
     * Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
     at java.nio.Bits.reserveMemory(Bits.java:658)
     at java.nio.DirectByteBuffer.<init>(DirectByteBuffer.java:123)
     at java.nio.ByteBuffer.allocateDirect(ByteBuffer.java:311)
     at com.study.directmemory.DirectByteBufferTest.main(DirectByteBufferTest.java:12)
     at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     at java.lang.reflect.Method.invoke(Method.java:497)
     at com.intellij.rt.execution.application.AppMain.main(AppMain.java:140)
     */
}
