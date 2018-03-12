package com.study.design_pattern.template;

/**
 * Created by tianyuzhi on 18/3/9.
 */
public class Client {
    public static void main(String...args) {
        String imageUrl = "http://img.my.csdn.net/uploads/xxxx.jpg";
        AbstractImageLoader imageLoader = new WebpImageLoader();
        imageLoader.downloadImage(imageUrl, 200, 200);
    }
}
