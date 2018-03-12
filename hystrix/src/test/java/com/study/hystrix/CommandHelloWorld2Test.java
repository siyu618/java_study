package com.study.hystrix;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutionException;

import static org.testng.Assert.*;

/**
 * Created by tianyuzhi on 17/9/19.
 */
public class CommandHelloWorld2Test {

    @Test
    public void testSynchronous() throws ExecutionException, InterruptedException {
        String str = new CommandHelloWorld2("World").construct().asObservable().toBlocking().toFuture().get();
        assert "Hello World".equals( new CommandHelloWorld2("World").construct().asObservable().toBlocking().toFuture().get());
        assert "Hello Bob".equals( new CommandHelloWorld2("Bob").construct().asObservable().toBlocking().toFuture().get());
    }

}