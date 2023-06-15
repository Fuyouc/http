package org.request.retrofit.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.PARAMETER})
public @interface URL {
    String value() default "";
}
