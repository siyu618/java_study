package com.study.network.socket;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;

/**
 * Created by tianyuzhi on 17/1/17.
 */
public class URLLocal {
    public static void main(String[] args) throws IOException {
        String file = "/tmp/url_local_test";
        FileUtils.writeLines(new File(file), Arrays.asList("123", "234", "345"));
        java.net.URL url = new java.net.URL("file:" + file);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        int data = inputStream.read();
        while (data != -1) {
            System.out.print((char)data);
            data = inputStream.read();
        }
        inputStream.close();
    }
}
