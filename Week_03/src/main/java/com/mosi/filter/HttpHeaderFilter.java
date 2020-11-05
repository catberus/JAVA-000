package com.mosi.filter;

import io.netty.handler.codec.http.HttpRequest;

public class HttpHeaderFilter {

    public void doFilter(HttpRequest request){
        request.headers().add("nio","liuzhao");
    }
}
