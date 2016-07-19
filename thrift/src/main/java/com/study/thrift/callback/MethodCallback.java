package com.study.thrift.callback;

import org.apache.thrift.async.AsyncMethodCallback;

import javax.naming.event.ObjectChangeListener;

/**
 * Created by tianyuzhi on 16/7/19.
 */
public class MethodCallback implements AsyncMethodCallback {
    Object response = null;
    public Object getResult() {
        return this.response;
    }
    @Override
    public void onComplete(Object response) {
        this.response = response;
    }

    @Override
    public void onError(Exception exception) {

    }
}
