package org.request.retrofit.annotations.handler.child;

import org.request.http.request.Request;
import org.request.retrofit.annotations.Param;
import org.request.retrofit.annotations.handler.RetrofitServiceHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 处理参数
 */
public class RetrofitParamHandler implements RetrofitServiceHandler {
    @Override
    public void handler(Request request, Method method, Object[] values) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Param param = parameter.getAnnotation(Param.class);
            String key = param == null ? parameter.getName() : "".equals(param.value()) ? parameter.getName() : param.value();
            request.addParam(key,values[i]);
        }
    }
}
