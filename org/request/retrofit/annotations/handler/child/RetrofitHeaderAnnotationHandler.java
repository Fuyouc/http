package org.request.retrofit.annotations.handler.child;

import org.request.http.request.Request;
import org.request.retrofit.annotations.Header;
import org.request.retrofit.annotations.Headers;
import org.request.retrofit.annotations.handler.RetrofitServiceHandler;

import java.lang.reflect.Method;

/**
 * 处理Header请求头
 */
public class RetrofitHeaderAnnotationHandler implements RetrofitServiceHandler {
    @Override
    public void handler(Request request, Method method, Object[] values) {
        if (method.getAnnotation(Header.class) != null){
            Header header = method.getAnnotation(Header.class);
            request.addHeader(header.key(),header.value());
        }
        if (method.getAnnotation(Headers.class) != null){
            Headers headers = method.getAnnotation(Headers.class);
            for (Header header : headers.header()) {
                request.addHeader(header.key(),header.value());
            }
        }
    }
}
