package com.itibo.vkcheck.Activity.models;

import java.util.Map;

/**
 * Created by erick on 27.8.15.
 */
public class RequestModel {

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }

    public String url;

    public String method;

    public Map<String, String> headers;

    public Map<String, String> body;
}