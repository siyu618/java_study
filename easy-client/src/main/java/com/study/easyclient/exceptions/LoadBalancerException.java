package com.study.easyclient.exceptions;

/**
 * @author jixu
 */
public class LoadBalancerException extends Exception {
    public LoadBalancerException(String msg) {
        super(msg);
    }

    public LoadBalancerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
