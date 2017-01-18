package com.study.network.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * Created by tianyuzhi on 17/1/17.
 */
public class URL {
    public static void main(String[] args) throws IOException {
        java.net.URL url = new java.net.URL("http://jenkov.com");
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
