package org.request.retrofit.proxy;

import org.request.http.Http;
import org.request.http.request.Request;
import org.request.http.request.XRequest;
import org.request.retrofit.Retrofit;

import org.request.retrofit.RetrofitFactor;
import org.request.retrofit.annotations.URL;
import org.request.retrofit.annotations.handler.RetrofitServiceHandler;
import org.request.retrofit.call.Call;
import org.request.retrofit.call.RetrofitCall;
import org.request.retrofit.exception.RetrofitException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ServerSocket;

/**
 * Retrofit代理
 */
public class RetrofitHttpProxy implements InvocationHandler {

    private Http http;
    private String service;

    public RetrofitHttpProxy(Class<?> serviceClass,Http http, String service) {
        this.http = http;
        if (serviceClass.getAnnotation(URL.class) != null){
            URL annotation = serviceClass.getAnnotation(URL.class);
            service = annotation.value();
        }
        if (service == null || "".equals(service)){
            throw new RetrofitException("service不能对于null或者空字符串");
        }
        service = service.endsWith("/") ? service : service + "/";
        this.service = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return run(method,args);
    }

    private Object run(Method method,Object[] parameterValue){
        Request request = new XRequest.Builder().build();

        for (RetrofitServiceHandler handler : RetrofitFactor.getHandlers()) {
            handler.handler(request,method,parameterValue);
        }

        String oldURL = request.getURL();

        oldURL = oldURL.startsWith("/") ? oldURL.substring(1,oldURL.length()) : oldURL;

        request.setURL(service + oldURL);

        return handler(request,method,parameterValue);
    }

    private Object handler(Request request,Method method,Object[] values){
        Type returnType = null;
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
            returnType = parameterizedType.getActualTypeArguments()[0];
        }
        org.request.http.request.call.Call call = http.newCall(request);
        return new RetrofitCall(returnType,call);
    }

}
