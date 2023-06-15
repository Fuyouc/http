package org.request.retrofit.annotations.handler;

import org.request.http.request.Request;

import java.lang.reflect.Method;

public interface RetrofitServiceHandler {
    void handler(Request request, Method method,Object[] values);
}
