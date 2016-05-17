package com.study.java8;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by tianyuzhi on 16/5/17.
 */
public class Base64Test {
    public static void main(String[] args) {
        try {
            // encode
            String base64encodeString = Base64.getEncoder().encodeToString("TutorialsPoint?java8".getBytes("utf-8"));
            System.out.println("Base64 Encoded String(Basic): " + base64encodeString);

            //decode
            byte[] base64decodedBytes = Base64.getDecoder().decode(base64encodeString);
            System.out.println("Original String:" + new String(base64decodedBytes));


            base64encodeString = Base64.getUrlEncoder().encodeToString("TutorialsPoint?java8".getBytes("utf-8"));
            System.out.println("Base64 Encoded String(URL):" + base64encodeString);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i ++) {
                sb.append(UUID.randomUUID().toString());
            }

            byte[] mineBytes = sb.toString().getBytes("utf-8");
            String mineEncodedString = Base64.getMimeEncoder().encodeToString(mineBytes);
            System.out.println("Base64 Encoded String (MIME):" + mineEncodedString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
