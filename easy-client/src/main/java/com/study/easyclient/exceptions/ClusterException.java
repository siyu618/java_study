package com.study.easyclient.exceptions;

/**
 * @author jixu
 */
public class ClusterException extends Exception {
    public ClusterException(String msg) {
        super(msg);
    }

    public ClusterException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}