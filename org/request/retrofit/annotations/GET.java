package org.request.retrofit.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GET {
    /**
     * 采用 GET 的请求方式
     * value为URI
     */
    String value() default "";
}
