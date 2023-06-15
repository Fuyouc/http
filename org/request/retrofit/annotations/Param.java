package org.request.retrofit.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {
    /**
     * 在被 @GET 标记的方法的参数列表上添加，value为key，如果为空使用参数名代替
     */
    String value() default "";
}
