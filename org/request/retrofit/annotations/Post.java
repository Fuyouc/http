package org.request.retrofit.annotations;

import org.request.http.request.MediaType;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Post {

    /**
     * 采用 POST 的请求方式
     * value为URI
     */
    String value() default "";

    /**
     * 设置ContentType
     */
    MediaType contentType() default MediaType.APPLICATION_JSON;
}
