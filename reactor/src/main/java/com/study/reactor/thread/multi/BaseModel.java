package com.study.reactor.thread.multi;

import com.study.reactor.config.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BaseModel implements Runnable {
    private ServerConfig serverConfig;
    public BaseModel(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverConfig.getServerPort());
            while (!Thread.interrupted()) {
                new Thread(new Handler(serverSocket.accept(), serverConfig)).start();
                // or use a thread pool here
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Handler implements Runnable {
        private Socket socket;
        private ServerConfig serverConfig;
        public Handler(Socket socket, ServerConfig serverConfig) {
            this.socket = socket;
            this.serverConfig = serverConfig;
        }

        @Override
        public void run() {
            try {
                byte[] input = new byte[serverConfig.getInputBufferSize()];
                socket.getInputStream().read(input);
                byte[] output = process(input);
                socket.getOutputStream().write(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private byte[] process(byte[] input) {
            byte[] output = ("Hello " + input).getBytes();
            return output;
        }
    }
}
