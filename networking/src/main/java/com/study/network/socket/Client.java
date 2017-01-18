package com.study.network.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by tianyuzhi on 17/1/17.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9000);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write(2);
        System.out.println("receieve:" + in.read());
        socket.close();
    }
}
