package com.study.java.distribute_architecture.ch01;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;

import static com.study.java.distribute_architecture.CloseUtil.close;

/**
 * Created by tianyuzhi on 18/6/26.
 */
public class ExporterTask implements Runnable {
    Socket client = null;
    public ExporterTask(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        ObjectInputStream input = null;
        ObjectOutputStream output = null;
        try {
            input = new ObjectInputStream(client.getInputStream());
            String interfaceName = input.readUTF();
            String methodName = input.readUTF();
            Class<?>[] parameterTypes = (Class<?>[])input.readObject();
            Object[] args = (Object[]) input.readObject();

            Class<?> service = Class.forName(interfaceName);
            Method method = service.getMethod(methodName, parameterTypes);
            Object result = method.invoke(service.newInstance(), args);
            output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(input);
            close(output);
            close(client);
        }
    }

}
