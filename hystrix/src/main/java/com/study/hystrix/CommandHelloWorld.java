package com.study.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by tianyuzhi on 17/9/18.
 */
public class CommandHelloWorld extends HystrixCommand<String> {
    private String name;
    protected CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello " + name + "!";
    }
}
