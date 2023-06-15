package org.request.retrofit.annotations.handler.child;

import org.request.http.request.Request;
import org.request.retrofit.annotations.Header;
import org.request.retrofit.annotations.Headers;
import org.request.retrofit.annotations.Param;
import org.request.retrofit.annotations.URL;
import org.request.retrofit.annotations.handler.RetrofitServiceHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.Map;

/**
 * 处理服务接口的参数
 */
public class RetrofitParamHandler implements RetrofitServiceHandler {
    @Override
    public void handler(Request request, Method method, Object[] values) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.getAnnotation(URL.class) != null){
                //如果是URL
                request.setURL((String) values[i]);
            }else if (parameter.getAnnotation(Headers.class) != null){
                //如果是Header
                Map<String,String> headers = (Map<String, String>) values[i];
                Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, String> entry = iterator.next();
                    request.addHeader(entry.getKey(),entry.getValue());
                }
            }else {
                Param param = parameter.getAnnotation(Param.class);
                String key = param == null ? parameter.getName() : "".equals(param.value()) ? parameter.getName() : param.value();
                request.addParam(key, values[i]);
            }
        }
    }
}
