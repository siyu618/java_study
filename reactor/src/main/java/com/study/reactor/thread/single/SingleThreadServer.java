package com.study.reactor.thread.single;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadServer {
    private static final int INPUT_BUFFER_SIZE = 20;

    private int port;
    private ServerSocket serverSocket;
    public SingleThreadServer(int port) {
        this.port = port;
    }

    public void run() throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            Socket client = serverSocket.accept();
            handle(client);
        }
    }

    private void handle(Socket client) throws IOException {
        // read
        byte[] input = new byte[INPUT_BUFFER_SIZE];
        int readNum = client.getInputStream().read(input);

        // process
        String res = "Hello " + input;

        // send
        client.getOutputStream().write(res.getBytes());

    }


}
