package org.request.http;

import org.request.http.config.NetWorkConfiguration;
import org.request.http.exception.HttpRequestException;
import org.request.http.request.MediaType;
import org.request.http.request.NetworkRequest;
import org.request.http.request.Request;
import org.request.http.request.call.Call;
import org.request.http.request.call.HttpCall;
import org.request.http.request.child.GetRequest;
import org.request.http.request.child.PostRequest;
import org.request.http.request.interceptor.HttpInterceptor;
import org.request.http.request.response.Response;

import java.net.HttpURLConnection;
import java.util.Map;

public final class Http {

    private HttpInterceptor interceptor;

    private NetWorkConfiguration configuration;

    public Http() {
        configuration = new NetWorkConfiguration(5000,5000,false);
    }

    public Call newCall(Request request){
        request.setConfiguration(configuration);
        return new HttpCall(request,interceptor);
    }

    public static class Builder{

        private Http http;

        public Builder() {
            http = new Http();
        }

        public Builder setInterceptor(HttpInterceptor interceptor){
            http.interceptor = interceptor;
            return this;
        }

        public Builder setReadTimeout(int timeout){
            http.configuration.setReadTimeout(timeout);
            return this;
        }

        public Builder setConnectionTimeout(int timeout){
            http.configuration.setConnectionTimeout(timeout);
            return this;
        }

        public Builder setUseCaches(boolean enable){
            http.configuration.setUseCaches(enable);
            return this;
        }

        public Http build(){
            return http;
        }
    }
}
