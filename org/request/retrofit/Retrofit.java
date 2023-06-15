package org.request.retrofit;

import org.request.http.Http;
import org.request.http.request.interceptor.HttpInterceptor;
import org.request.retrofit.annotations.handler.RetrofitServiceHandler;
import org.request.retrofit.proxy.RetrofitHttpProxy;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Retrofit（封装Http库实现）
 */
public final class Retrofit {

    /**
     * 服务器地址
     */
    private String service;
    private Http http;

    /**
     * 获取服务接口
     */
    public <T> T getService(Class<T> serviceClass){
        if (serviceClass.isInterface()){
            Object proxy = Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass}, new RetrofitHttpProxy(serviceClass,http,service));
            return (T) proxy;
        }
        throw new IllegalArgumentException(serviceClass.getName() + "不是一个接口，无法进行动态代理");
    }

    public static class Builder{
        private Retrofit retrofit;
        private Http.Builder builder;

        public Builder() {
            retrofit = new Retrofit();
            builder = new Http.Builder();
        }

        public Builder setInterceptor(HttpInterceptor interceptor){
            builder.setInterceptor(interceptor);
            return this;
        }

        public Builder setReadTimeout(int timeout){
            builder.setReadTimeout(timeout);
            return this;
        }

        public Builder setConnectionTimeout(int timeout){
            builder.setConnectionTimeout(timeout);
            return this;
        }

        public Builder setUseCaches(boolean enable){
            builder.setUseCaches(enable);
            return this;
        }

        public Builder setService(String url){
            retrofit.service = url;
            return this;
        }

        public Retrofit build(){
            retrofit.http = builder.build();
            return retrofit;
        }
    }
}
