package org.request.retrofit.annotations.handler.child;

import org.request.http.request.Request;
import org.request.retrofit.annotations.Post;
import org.request.retrofit.annotations.handler.RetrofitServiceHandler;

import java.lang.reflect.Method;

public class RetrofitPostAnnotationHandler implements RetrofitServiceHandler {
    @Override
    public void handler(Request request, Method method, Object[] values) {
        if (method.getAnnotation(Post.class) != null){
            Post post = method.getAnnotation(Post.class);
            request.setMethod("POST");
            request.setURL(post.value());
            request.setContentType(post.contentType());
        }
    }
}
