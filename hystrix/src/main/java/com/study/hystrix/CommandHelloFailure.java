package com.study.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by tianyuzhi on 17/9/19.
 */
public class CommandHelloFailure extends HystrixCommand<String> {
    private final String name;

    public CommandHelloFailure(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }
    @Override
    protected String run() throws Exception {
        throw new RuntimeException("this command always fails");
    }
    @Override
    protected String getFallback() {
        return "Hello Failure " + name + "!";
    }
}
