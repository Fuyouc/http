package org.request.retrofit.annotations.handler.child;

import org.request.http.request.Request;
import org.request.retrofit.annotations.GET;
import org.request.retrofit.annotations.handler.RetrofitServiceHandler;

import java.lang.reflect.Method;

public class RetrofitGetAnnotationHandler implements RetrofitServiceHandler {
    @Override
    public void handler(Request request, Method method, Object[] values) {
        if (method.getAnnotation(GET.class) != null){
            GET get = method.getAnnotation(GET.class);
            request.setMethod("GET");
            request.setURL(get.value());
        }
    }
}
