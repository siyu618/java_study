package com.study.hystrix;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by tianyuzhi on 17/9/19.
 */
public class CommandHelloFailureTest {

    @Test
    public void testSynchronous() {
        assert "Hello Failure World!".equals(new CommandHelloFailure("World").execute());
        assert "Hello Failure Bob!".equals(new CommandHelloFailure("Bob").execute());
    }

}