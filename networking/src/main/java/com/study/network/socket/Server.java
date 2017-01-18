package com.study.network.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tianyuzhi on 17/1/17.
 */
public class Server {


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        boolean isStoped = false;
        AtomicInteger counter = new AtomicInteger(10);
        while (!isStoped) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                try {
                    BufferedInputStream in = new BufferedInputStream(clientSocket.getInputStream());
                    BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream());
                    int a = in.read();
                    System.out.println(Thread.currentThread().getName()  +" recieve :" +  a);
                    out.write(2);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }, "name" + counter.getAndIncrement()).start();
        }
    }
}
