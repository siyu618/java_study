package com.study.network.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by tianyuzhi on 17/1/17.
 */
public class InternetAddress {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getByName("jenkov.com");

        InetAddress address1 = InetAddress.getByName("78.46.84.171");
        InetAddress address2 = InetAddress.getLocalHost();


        System.out.println(address);
        System.out.println(address1);
        System.out.println(address2);
    }
}
