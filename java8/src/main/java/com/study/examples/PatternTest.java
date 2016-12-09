package com.study.examples;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by tianyuzhi on 16/12/8.
 */
public class PatternTest {
    public static void main(String[] args ){
        String PATTERN_STR_1 = "^(http|https)://.*\\.ifeng\\.com/";
        String PATTERN_STR_2 = "^(http|https)://(tv|diantai)\\.ifeng\\.com/";

         PATTERN_STR_1 = "^((http|https)://|).*\\.ifeng\\.com/.*";
         PATTERN_STR_2 = "^((http|https)://|)(tv|diantai)\\.ifeng\\.com/.*";
        Pattern pattern1 = Pattern.compile(PATTERN_STR_1);
        Pattern pattern2 = Pattern.compile(PATTERN_STR_2);

        List<String> urls  = Arrays.asList(
                "inews.ifeng.com/yidian/50365577/news.shtml?ch=ref_zbs_ydzx_news",
                "http://tv.ifeng.com/yidian/50365577/news.shtml?ch=ref_zbs_ydzx_news",
                "http://diantai.ifeng.com/yidian/50365577/news.shtml?ch=ref_zbs_ydzx_news",
                "http://inews.ifeng.com/yidian/50365577/news.shtml?ch=ref_zbs_ydzx_news",
                "https://inews.ifeng.com/yidian/50365577/news.shtml?ch=ref_zbs_ydzx_news",
                "http2://inews.ifeng.com/yidian/50365577/news.shtml?ch=ref_zbs_ydzx_news",
                "http1://inews.ifeng.com/yidian/50365577/news.shtml?ch=ref_zbs_ydzx_news",
                ""
               // null
                );

        for (String url : urls) {
            boolean match1 = pattern1.matcher(url).matches();
            boolean match2 = pattern2.matcher(url).matches();
            System.out.println(url );
            System.out.println(match1 + ":" + match2 );
        }
    }
}
