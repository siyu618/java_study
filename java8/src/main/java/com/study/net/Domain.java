package com.study.net;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

/**
 * Created by tianyuzhi on 17/8/21.
 */
public class Domain {
    public static boolean hasURLContentChanged(String url, long fromtime)
    {
        URL Url; HttpURLConnection httpconn=null; long modtime;
        boolean contentChanged=false;
        try {
            Url = new URL(url);
            httpconn = (HttpURLConnection) Url.openConnection();
            modtime = httpconn.getLastModified();
            if ((modtime > fromtime) || (modtime <= 0)) {
                System.out.println("*** Old Link time:"+new Date(fromtime).toString()+
                        " New link time:"+new Date(modtime).toString());
                contentChanged=true;
            }
        }
        catch (Exception e) {
            contentChanged=true; // assume content has changed
        }
        if (httpconn != null)
            httpconn.disconnect();
        return contentChanged;
    }

    public static long getURLLastModified(String url)
    {
        URL Url; HttpURLConnection httpconn=null; long modtime;
        try {
            Url = new URL(url);
            httpconn = (HttpURLConnection) Url.openConnection();
            modtime = httpconn.getLastModified();
        }
        catch (Exception e) {
            modtime = -1;
        }
        if (httpconn != null)
            httpconn.disconnect();
        System.out.println("URL:"+url+" LastModified:" +
                new Date(modtime).toString());
        return modtime;
    }

    public static String getDomainName(String url)
    {
        URL u;
        try {
            u = new URL(url);
        }
        catch (Exception e) {
            return "";
        }
        return u.getHost();
    }

    public static void main(String[] args) {
        List<String> urls = Arrays.asList("http://36kr.com/coop/yidian/post/5085663.html?ktm_source=yidian",
                "http://krr.36kr.com/coop/yidian/post/5085663.html?ktm_source=yidian");

        for (String url : urls) {
            System.out.println(getDomainName(url));
        }
    }
}
