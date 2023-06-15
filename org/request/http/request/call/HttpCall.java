package org.request.http.request.call;

import org.request.http.HttpRequestCallBack;
import org.request.http.exception.HttpRequestException;
import org.request.http.request.NetworkRequest;
import org.request.http.request.Request;
import org.request.http.request.child.GetRequest;
import org.request.http.request.child.PostRequest;
import org.request.http.request.interceptor.HttpInterceptor;
import org.request.http.request.response.Response;
import org.request.retrofit.response.XFailureResponse;

import java.net.HttpURLConnection;
import java.util.concurrent.CompletableFuture;

public final class HttpCall implements Call{

    private Request request;
    private HttpInterceptor interceptor;

    public HttpCall(Request request, HttpInterceptor interceptor) {
        this.request = request;
        this.interceptor = interceptor;
    }

    @Override
    public Response execute() {
        checkRequest(request);

        if (request.getMethod().equalsIgnoreCase("POST") && request.getContentType() != null){
            request.addHeader("Content-Type", request.getContentType().getName());
        }

        if (interceptor != null){
            request = interceptor.requestInterceptor(request);
        }

        Response response = getNetWorkRequest(request.getMethod()).request(request);

        if (interceptor != null){
            response = interceptor.responseInterceptor(response);
        }

        return response;
    }

    @Override
    public void enqueue(HttpRequestCallBack callBack) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                checkRequest(request);

                if (request.getMethod().equalsIgnoreCase("POST") && request.getContentType() != null){
                    request.addHeader("Content-Type", request.getContentType().getName());
                }

                if (interceptor != null){
                    request = interceptor.requestInterceptor(request);
                }

                Response response = getNetWorkRequest(request.getMethod()).request(request);

                if (interceptor != null){
                    response = interceptor.responseInterceptor(response);
                }

                if (callBack != null){
                    if (response.getCode() == HttpURLConnection.HTTP_OK){
                        //状态码为200
                        callBack.onResponse(response);
                    }else {
                        //错误状态码
                        callBack.onFailure(new XFailureResponse(response));
                    }
                }
            }
        });
        thread.start();
    }

    private void checkRequest(Request request){
        if (null == request.getURL() || "".equals(request.getURL())){
            throw new HttpRequestException("URL不允许为空");
        }
    }

    private NetworkRequest getNetWorkRequest(String method){
        switch (method){
            case "GET":
                return new GetRequest();
            case "POST":
                return new PostRequest();
            default:
                return new GetRequest();
        }
    }
}
