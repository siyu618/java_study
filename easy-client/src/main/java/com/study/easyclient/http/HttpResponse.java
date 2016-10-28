package com.study.easyclient.http;

/**
 * @author jixu
 */
public class HttpResponse {
    public final String responseBody;
    public final int statusCode;
    public HttpResponse(String responseBody, int statusCode) {
        this.responseBody = responseBody;
        this.statusCode = statusCode;
    }
}
