package com.study.nio;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * Created by tianyuzhi on 17/1/19.
 */
public class ScatterAndGather {
    public static final String file = "/tmp/java_nio_scatter.txt";

    public ScatterAndGather() throws IOException {
        FileUtils.writeLines(new File(file),
                Arrays.asList(
                        "header:1234567890",
                        "body:2345678901",
                        "3456789012"));
    }




    public static String printBuffer(ByteBuffer buffer) {
        StringBuilder sb = new StringBuilder(buffer.capacity());
        if (null != buffer) {
            while (buffer.hasRemaining()) {
                //System.out.print((char)buffer.get());
                sb.append((char)buffer.get());
            }
        }
        return sb.toString();

    }

    public void gather() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel outChannel = randomAccessFile.getChannel();

        ByteBuffer header = ByteBuffer.allocate(18);
        ByteBuffer body = ByteBuffer.allocate(1024);

        header.put("header:1234567890".getBytes());
        header.flip();
        body.put("body:23456789013456789012".getBytes());
        body.flip();
        ByteBuffer[] bufferArray = { header, body };

        outChannel.write(bufferArray);
    }

    public void scatter() throws IOException {


        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel inChannel = randomAccessFile.getChannel();

        ByteBuffer header = ByteBuffer.allocate(18);
        ByteBuffer body = ByteBuffer.allocate(1024);

        ByteBuffer[] byteBufferArray = {header, body};
        inChannel.read(byteBufferArray);
        header.flip();
        body.flip();
        System.out.println("HEADER:" + printBuffer(header));
        System.out.println("BODY:" + printBuffer(body));

    }

    public static void main(String[] args) throws IOException {

        ScatterAndGather scatterAndGather = new ScatterAndGather();
        scatterAndGather.scatter();
        scatterAndGather.gather();
        scatterAndGather.scatter();
    }
}
