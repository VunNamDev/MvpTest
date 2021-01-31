package com.example.mpvtest.model;

public class BaseApi {
    public BaseApi() {
    }

    public BaseApi(String url, Method method) {
        this.url = url;

        this.method = method;
    }

    private Method method;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
}
