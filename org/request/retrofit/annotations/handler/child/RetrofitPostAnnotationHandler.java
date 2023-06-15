package org.request.retrofit.annotations.handler.child;

import org.request.http.request.Request;
import org.request.retrofit.annotations.POST;
import org.request.retrofit.annotations.handler.RetrofitServiceHandler;

import java.lang.reflect.Method;

public class RetrofitPostAnnotationHandler implements RetrofitServiceHandler {
    @Override
    public void handler(Request request, Method method, Object[] values) {
        if (method.getAnnotation(POST.class) != null){
            POST post = method.getAnnotation(POST.class);
            request.setMethod("POST");
            request.setURL(post.value());
            request.setContentType(post.contentType());
        }
    }
}
