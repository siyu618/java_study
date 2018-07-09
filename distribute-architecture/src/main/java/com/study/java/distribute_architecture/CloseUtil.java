package com.study.java.distribute_architecture;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by tianyuzhi on 18/6/26.
 */
public class CloseUtil {
    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
