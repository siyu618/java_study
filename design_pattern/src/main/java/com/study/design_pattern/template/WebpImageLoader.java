package com.study.design_pattern.template;

/**
 *
 * @author tianyuzhi
 * @date 18/3/9
 */
public class WebpImageLoader extends AbstractImageLoader {
    @Override
    protected String getUrl(String imageUrl, int width, int height) {
        return String.format("%s?imageView2/1/w/%d/h/%d/format/webp", imageUrl, width, height);
    }
}
