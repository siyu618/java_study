package com.study.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by tianyuzhi on 17/6/27.
 */
public class HotSwapClassLoader extends URLClassLoader{
   public HotSwapClassLoader(URL[] urls) {
        super(urls);
    }

    /*
    public HotSwapClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public Class reload(String name, boolean resolve) {
        return HotSwapClassLoader(
                super.getURLs(),
                super.getParent().loadClass(name, resolve));
    }*/
}
