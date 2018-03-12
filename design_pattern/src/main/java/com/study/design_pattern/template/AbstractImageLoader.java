package com.study.design_pattern.template;

/**
 *
 * @author tianyuzhi
 * @date 18/3/9
 */
public abstract class AbstractImageLoader {
    public final void downloadImage(String imageUrl, int width, int height) {
        String finalImageUrl = getUrl(imageUrl, width, height);
        System.out.println("start to download:" + finalImageUrl);
    }

    protected abstract String getUrl(String imageUrl, int width, int height);
}
