package com.study.nio;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.concurrent.Future;

/**
 * Created by tianyuzhi on 17/1/20.
 */
public class AsynchronousFileChannelTest {
    private static final String file = "async_file";

    public AsynchronousFileChannelTest() throws IOException {
        FileUtils.writeLines(new File(file),
                Arrays.asList(
                        "1234567890",
                        "2345678901",
                        "3456789012",
                        "==========",
                        "3456789012"));
    }


    public void read() throws IOException {
        Path path = Paths.get(file);
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(15);
        long position = 0;
        Future<Integer> operation = channel.read(buffer, position);
        while (!operation.isDone()) { // if the buffer size is smaller the file size, it will only ready that many.
            //
        }
        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));
        buffer.clear();
    }

    public void read2() throws IOException {
        Path path = Paths.get(file);
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(15);
        long position = 0;
        channel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result = " + result);

                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });

//        buffer.flip();
//        byte[] data = new byte[buffer.limit()];
//        buffer.get(data);
//        System.out.println(new String(data));
//        buffer.clear();
    }


    public static void main(String[] args) throws IOException {
        AsynchronousFileChannelTest test = new AsynchronousFileChannelTest();
        test.read();
        System.out.println("=====");
        test.read2();
    }
}
