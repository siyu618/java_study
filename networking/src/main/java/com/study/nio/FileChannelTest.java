package com.study.nio;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * Created by tianyuzhi on 17/1/19.
 */
public class FileChannelTest {
    public static void main(String[] args) throws IOException {
        String file = "/tmp/java_nio_study.txt";
        FileUtils.writeLines(new File(file),
                Arrays.asList(
                        "1234567890",
                        "2345678901",
                        "3456789012"));

        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel inChannel = randomAccessFile.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(15);
        int nBytes = inChannel.read(buffer);
        while (nBytes != -1) {
            System.out.println("read: " + nBytes);
            buffer.flip();

            while (buffer.hasRemaining()) {
                System.out.print((char)buffer.get());
            }
            buffer.clear();
            nBytes = inChannel.read(buffer);
        }
        randomAccessFile.close();
    }
}
