package org.request.retrofit;

import org.request.retrofit.annotations.Header;
import org.request.retrofit.annotations.handler.child.RetrofitGetAnnotationHandler;
import org.request.retrofit.annotations.handler.child.RetrofitHeaderAnnotationHandler;
import org.request.retrofit.annotations.handler.child.RetrofitParamHandler;
import org.request.retrofit.annotations.handler.child.RetrofitPostAnnotationHandler;
import org.request.retrofit.annotations.handler.RetrofitServiceHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Retrofit工厂
 */
public final class RetrofitFactor {
    //Retrofit注解处理器
    private static List<RetrofitServiceHandler> handlers;

    static {
        //在此注册处理器的功能
        handlers = new ArrayList<>();
        handlers.add(new RetrofitGetAnnotationHandler());
        handlers.add(new RetrofitPostAnnotationHandler());
        handlers.add(new RetrofitParamHandler());
        handlers.add(new RetrofitHeaderAnnotationHandler());
    }

    public static List<RetrofitServiceHandler> getHandlers() {
        return handlers;
    }
}
